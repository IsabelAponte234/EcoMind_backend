package pe.greenminds.ecomind_backend.quests.application.internal.commandservices;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.greenminds.ecomind_backend.quests.application.commandservices.ActivityUserCommandService;
import pe.greenminds.ecomind_backend.quests.application.internal.submission.ActivitySubmissionHandlerRegistry;
import pe.greenminds.ecomind_backend.quests.domain.model.aggregates.ActivityUser;
import pe.greenminds.ecomind_backend.quests.domain.model.commands.CreateActivityUserCommand;
import pe.greenminds.ecomind_backend.quests.domain.model.commands.SubmitActivityUserCommand;
import pe.greenminds.ecomind_backend.quests.domain.model.valueobjects.QuestStatus;
import pe.greenminds.ecomind_backend.quests.domain.repositories.ActivityRepository;
import pe.greenminds.ecomind_backend.quests.domain.repositories.ActivityUserRepository;
import pe.greenminds.ecomind_backend.quests.domain.repositories.QuestUserRepository;
import pe.greenminds.ecomind_backend.shared.application.result.ApplicationError;
import pe.greenminds.ecomind_backend.shared.application.result.Result;

@Service
public class ActivityUserCommandServiceImpl implements ActivityUserCommandService {
    private final ActivityUserRepository activityUserRepository;
    private final ActivityRepository activityRepository;
    private final QuestUserRepository questUserRepository;
    private final ActivitySubmissionHandlerRegistry submissionHandlerRegistry;

    public ActivityUserCommandServiceImpl(
            ActivityUserRepository activityUserRepository,
            ActivityRepository activityRepository,
            QuestUserRepository questUserRepository,
            ActivitySubmissionHandlerRegistry submissionHandlerRegistry
    ) {
        this.activityUserRepository = activityUserRepository;
        this.activityRepository = activityRepository;
        this.questUserRepository = questUserRepository;
        this.submissionHandlerRegistry = submissionHandlerRegistry;
    }

    @Override
    public Result<ActivityUser, ApplicationError> handle(CreateActivityUserCommand command) {
        var questUser = questUserRepository.findById(command.questUserId());
        if (questUser.isEmpty()) {
            return Result.failure(
                    ApplicationError.notFound("QuestUser", command.questUserId().toString())
            );
        }

        var activity = activityRepository.findById(command.activityId());
        if (activity.isEmpty()) {
            return Result.failure(
                    ApplicationError.notFound("Activity", command.activityId().toString())
            );
        }

        if (activityUserRepository.existsByQuestUserIdAndActivityId(
                command.questUserId(),
                command.activityId()
        )) {
            return Result.failure(
                    ApplicationError.conflict(
                            "ActivityUser",
                            "The activity is already assigned to this quest user"
                    )
            );
        }

        if (!questUser.get().getQuestId().equals(activity.get().getQuestId())) {
            return Result.failure(
                    ApplicationError.businessRuleViolation(
                            "Activity must belong to the assigned quest",
                            "The activity and quest user reference different quests"
                    )
            );
        }

        try {
            var activityUser = new ActivityUser(
                    command.questUserId(),
                    command.activityId(),
                    activity.get().getDescription(),
                    activity.get().getActivityConfiguration(),
                    command.collaborativeSessionId()
            );
            return Result.success(activityUserRepository.save(activityUser));
        } catch (IllegalArgumentException | NullPointerException exception) {
            return Result.failure(
                    ApplicationError.validationError("ActivityUser", exception.getMessage())
            );
        } catch (Exception exception) {
            return Result.failure(
                    ApplicationError.unexpected("ActivityUser creation", exception.getMessage())
            );
        }
    }

    @Override
    @Transactional
    public Result<ActivityUser, ApplicationError> handle(SubmitActivityUserCommand command) {
        var activityUser = activityUserRepository.findById(command.activityUserId());
        if (activityUser.isEmpty()) {
            return Result.failure(
                    ApplicationError.notFound(
                            "ActivityUser",
                            command.activityUserId().toString()
                    )
            );
        }

        var questUser = questUserRepository.findById(activityUser.get().getQuestUserId());
        if (questUser.isEmpty()) {
            return Result.failure(
                    ApplicationError.notFound(
                            "QuestUser",
                            activityUser.get().getQuestUserId().toString()
                    )
            );
        }

        if (questUser.get().getStatus() == QuestStatus.COMPLETED) {
            return Result.failure(
                    ApplicationError.businessRuleViolation(
                            "Completed quest attempts cannot be modified",
                            "The quest is already completed"
                    )
            );
        }

        var activity = activityRepository.findById(activityUser.get().getActivityId());
        if (activity.isEmpty()) {
            return Result.failure(
                    ApplicationError.notFound(
                            "Activity",
                            activityUser.get().getActivityId().toString()
                    )
            );
        }

        var submissionHandler = submissionHandlerRegistry.findByType(
                activity.get().getActivityType()
        );
        if (submissionHandler.isEmpty()) {
            return Result.failure(
                    ApplicationError.businessRuleViolation(
                            "Unsupported activity type",
                            "Activity type: " + activity.get().getActivityType()
                    )
            );
        }

        try {
            submissionHandler.get().submit(
                    activityUser.get(),
                    command.data()
            );
        } catch (IllegalArgumentException exception) {
            return Result.failure(
                    ApplicationError.validationError(
                            "Activity submission",
                            exception.getMessage()
                    )
            );
        }

        var savedActivityUser = activityUserRepository.save(activityUser.get());
        var activityUsers = activityUserRepository.findByQuestUserId(
                questUser.get().getId()
        );
        var questProgress = activityUsers.stream()
                .mapToDouble(ActivityUser::getProgress)
                .average()
                .orElse(0.0);

        questUser.get().updateProgress(questProgress);
        questUserRepository.save(questUser.get());

        return Result.success(savedActivityUser);
    }
}
