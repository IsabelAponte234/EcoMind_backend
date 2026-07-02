package pe.greenminds.ecomind_backend.quests.application.internal.commandservices;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import pe.greenminds.ecomind_backend.quests.application.commandservices.QuestUserCommandService;
import pe.greenminds.ecomind_backend.quests.domain.model.aggregates.ActivityUser;
import pe.greenminds.ecomind_backend.quests.domain.model.aggregates.QuestUser;
import pe.greenminds.ecomind_backend.quests.domain.model.commands.CompleteQuestUserCommand;
import pe.greenminds.ecomind_backend.quests.domain.model.commands.CreateQuestUserCommand;
import pe.greenminds.ecomind_backend.quests.domain.model.commands.DeleteQuestUserCommand;
import pe.greenminds.ecomind_backend.quests.domain.model.valueobjects.CollabMemberStatus;
import pe.greenminds.ecomind_backend.quests.domain.model.valueobjects.CollabQuestStatus;
import pe.greenminds.ecomind_backend.quests.domain.model.valueobjects.QuestStatus;
import pe.greenminds.ecomind_backend.quests.domain.repositories.ActivityRepository;
import pe.greenminds.ecomind_backend.quests.domain.repositories.ActivityUserRepository;
import pe.greenminds.ecomind_backend.quests.domain.repositories.CollabQuestMemberRepository;
import pe.greenminds.ecomind_backend.quests.domain.repositories.CollabQuestSessionRepository;
import pe.greenminds.ecomind_backend.quests.domain.repositories.QuestRepository;
import pe.greenminds.ecomind_backend.quests.domain.repositories.QuestUserRepository;
import pe.greenminds.ecomind_backend.shared.application.result.ApplicationError;
import pe.greenminds.ecomind_backend.shared.application.result.Result;

@Service
public class QuestUserCommandServiceImpl implements QuestUserCommandService {
    private final QuestUserRepository questUserRepository;
    private final QuestRepository questRepository;
    private final ActivityRepository activityRepository;
    private final ActivityUserRepository activityUserRepository;
    private final CollabQuestSessionRepository collabQuestSessionRepository;
    private final CollabQuestMemberRepository collabQuestMemberRepository;

    public QuestUserCommandServiceImpl(
            QuestUserRepository questUserRepository,
            QuestRepository questRepository,
            ActivityUserRepository activityUserRepository,
            ActivityRepository activityRepository,
            CollabQuestSessionRepository collabQuestSessionRepository,
            CollabQuestMemberRepository collabQuestMemberRepository
    ) {
        this.questUserRepository = questUserRepository;
        this.questRepository = questRepository;
        this.activityUserRepository = activityUserRepository;
        this.activityRepository = activityRepository;
        this.collabQuestSessionRepository = collabQuestSessionRepository;
        this.collabQuestMemberRepository = collabQuestMemberRepository;
    }

    @Transactional
    @Override
    public Result<QuestUser, ApplicationError> handle(CreateQuestUserCommand command) {
        if (!questRepository.existsById(command.questId())) {
            return Result.failure(
                    ApplicationError.notFound("Quest", command.questId().toString())
            );
        }

        if (activityRepository.countByQuestId(command.questId()) < 1) {
            return Result.failure(
                    ApplicationError.businessRuleViolation(
                            "Quest must have at least one activity",
                            "Quest %d has no activities".formatted(command.questId())
                    )
            );
        }

        if (questUserRepository.existsByUserIdAndQuestId(command.userId(), command.questId())) {
            return Result.failure(
                    ApplicationError.conflict(
                            "QuestUser",
                            "The quest is already assigned to this user"
                    )
            );
        }

        try {
            var questUser = new QuestUser(
                    command.userId(),
                    command.questId(),
                    command.collaborativeSessionId()
            );

            var savedQuestUser = questUserRepository.save(questUser);
            var activities = activityRepository.findByQuestsIdOrderByOrderAsc(
                    savedQuestUser.getQuestId()
            );

            for(var activity : activities) {
                if(!activityUserRepository.existsByQuestUserIdAndActivityId(savedQuestUser.getId(), activity.getId())) {
                    activityUserRepository.save(
                            new ActivityUser(
                                    savedQuestUser.getId(),
                                    activity.getId(),
                                    activity.getDescription(),
                                    activity.getActivityConfiguration(),
                                    savedQuestUser.getCollaborativeSessionId()
                            )
                    );
                }
            }

            return Result.success(savedQuestUser);

        } catch (IllegalArgumentException | NullPointerException exception) {
            return Result.failure(
                    ApplicationError.validationError("QuestUser", exception.getMessage())
            );
        } catch (Exception exception) {
            return Result.failure(
                    ApplicationError.unexpected("QuestUser creation", exception.getMessage())
            );
        }
    }

    @Transactional
    @Override
    public Result<QuestUser, ApplicationError> handle(DeleteQuestUserCommand command) {
        var questUser = questUserRepository.findById(command.questUserId());

        if (questUser.isEmpty()) {
            return Result.failure(
                    ApplicationError.notFound("QuestUser", command.questUserId().toString())
            );
        }

        if (questUser.get().getCollaborativeSessionId() != null) {
            handleCollaborativeQuestUserDeletion(questUser.get());
        }

        activityUserRepository.deleteByQuestUserId(command.questUserId());
        questUserRepository.deleteById(command.questUserId());
        return Result.success(questUser.get());
    }

    @Transactional
    @Override
    public Result<QuestUser, ApplicationError> handle(CompleteQuestUserCommand command) {
        var questUser = questUserRepository.findById(command.questUserId());

        if (questUser.isEmpty()) {
            return Result.failure(
                    ApplicationError.notFound("QuestUser", command.questUserId().toString())
            );
        }

        if (questUser.get().getCollaborativeSessionId() != null) {
            return completeCollaborativeQuest(questUser.get());
        }

        try {
            questUser.get().complete();
            return Result.success(questUserRepository.save(questUser.get()));
        } catch (IllegalStateException exception) {
            return Result.failure(
                    ApplicationError.businessRuleViolation(
                            "Quest is not ready to complete",
                            exception.getMessage()
                    )
            );
        }
    }

    private Result<QuestUser, ApplicationError> completeCollaborativeQuest(
            QuestUser requestedQuestUser
    ) {
        var session = collabQuestSessionRepository.findById(
                requestedQuestUser.getCollaborativeSessionId()
        );
        if (session.isEmpty()) {
            return Result.failure(
                    ApplicationError.notFound(
                            "CollabQuestSession",
                            requestedQuestUser.getCollaborativeSessionId().toString()
                    )
            );
        }

        if (session.get().getStatus() != CollabQuestStatus.STARTED) {
            return Result.failure(
                    ApplicationError.businessRuleViolation(
                            "Collaborative quest session must be started",
                            "Session %d is %s".formatted(
                                    session.get().getId(),
                                    session.get().getStatus()
                            )
                    )
            );
        }

        var acceptedMembers = collabQuestMemberRepository.findBySessionIdAndStatusIn(
                session.get().getId(),
                java.util.List.of(CollabMemberStatus.ACCEPTED)
        );
        var requesterIsAccepted = acceptedMembers.stream()
                .anyMatch(member -> member.getUserId().equals(requestedQuestUser.getUserId()));

        if (!requesterIsAccepted) {
            return Result.failure(
                    ApplicationError.businessRuleViolation(
                            "Only accepted members can complete collaborative quests",
                            "User %d is not accepted in session %d".formatted(
                                    requestedQuestUser.getUserId(),
                                    session.get().getId()
                            )
                    )
            );
        }

        var questUsers = new java.util.ArrayList<QuestUser>();
        for (var member : acceptedMembers) {
            var questUser = questUserRepository.findByUserIdAndQuestId(
                    member.getUserId(),
                    session.get().getQuestId()
            );

            if (questUser.isEmpty()
                    || !session.get().getId().equals(
                            questUser.get().getCollaborativeSessionId()
                    )) {
                return Result.failure(
                        ApplicationError.notFound(
                                "QuestUser",
                                "userId=%d, questId=%d, collaborativeSessionId=%d".formatted(
                                        member.getUserId(),
                                        session.get().getQuestId(),
                                        session.get().getId()
                                )
                        )
                );
            }

            if (questUser.get().getStatus() != QuestStatus.READY_TO_COMPLETE) {
                return Result.failure(
                        ApplicationError.businessRuleViolation(
                                "All collaborative quest users must be ready to complete",
                                "QuestUser %d is %s".formatted(
                                        questUser.get().getId(),
                                        questUser.get().getStatus()
                                )
                        )
                );
            }

            questUsers.add(questUser.get());
        }

        try {
            QuestUser savedRequestedQuestUser = null;
            for (var questUser : questUsers) {
                questUser.complete();
                var savedQuestUser = questUserRepository.save(questUser);
                if (questUser.getId().equals(requestedQuestUser.getId())) {
                    savedRequestedQuestUser = savedQuestUser;
                }
            }

            session.get().complete();
            collabQuestSessionRepository.save(session.get());

            return Result.success(savedRequestedQuestUser);
        } catch (IllegalStateException exception) {
            return Result.failure(
                    ApplicationError.businessRuleViolation(
                            "Quest is not ready to complete",
                            exception.getMessage()
                    )
            );
        }
    }

    private void handleCollaborativeQuestUserDeletion(QuestUser questUser) {
        var session = collabQuestSessionRepository.findById(
                questUser.getCollaborativeSessionId()
        );

        if (session.isEmpty() || session.get().getStatus() != CollabQuestStatus.STARTED) {
            return;
        }

        var member = collabQuestMemberRepository.findBySessionIdAndUserId(
                session.get().getId(),
                questUser.getUserId()
        );

        if (member == null || member.getStatus() != CollabMemberStatus.ACCEPTED) {
            return;
        }

        member.leaveQuest();
        collabQuestMemberRepository.save(member);

        var acceptedMembers = collabQuestMemberRepository.findBySessionIdAndStatusIn(
                session.get().getId(),
                java.util.List.of(CollabMemberStatus.ACCEPTED)
        );
        if (acceptedMembers.isEmpty()) {
            session.get().cancel();
            collabQuestSessionRepository.save(session.get());
        }
    }
}
