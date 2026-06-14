package pe.greenminds.ecomind_backend.quests.application.internal.commandservices;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import pe.greenminds.ecomind_backend.quests.application.commandservices.QuestUserCommandService;
import pe.greenminds.ecomind_backend.quests.domain.model.aggregates.ActivityUser;
import pe.greenminds.ecomind_backend.quests.domain.model.aggregates.QuestUser;
import pe.greenminds.ecomind_backend.quests.domain.model.commands.CompleteQuestUserCommand;
import pe.greenminds.ecomind_backend.quests.domain.model.commands.CreateQuestUserCommand;
import pe.greenminds.ecomind_backend.quests.domain.model.commands.DeleteQuestUserCommand;
import pe.greenminds.ecomind_backend.quests.domain.repositories.ActivityRepository;
import pe.greenminds.ecomind_backend.quests.domain.repositories.ActivityUserRepository;
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

    public QuestUserCommandServiceImpl(
            QuestUserRepository questUserRepository,
            QuestRepository questRepository,
            ActivityUserRepository activityUserRepository,
            ActivityRepository activityRepository
    ) {
        this.questUserRepository = questUserRepository;
        this.questRepository = questRepository;
        this.activityUserRepository = activityUserRepository;
        this.activityRepository = activityRepository;
    }

    @Transactional
    @Override
    public Result<QuestUser, ApplicationError> handle(CreateQuestUserCommand command) {
        if (!questRepository.existsById(command.questId())) {
            return Result.failure(
                    ApplicationError.notFound("Quest", command.questId().toString())
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
}
