package pe.greenminds.ecomind_backend.quests.application.internal.commandservices;

import org.springframework.stereotype.Service;
import pe.greenminds.ecomind_backend.quests.application.commandservices.ActivityUserCommandService;
import pe.greenminds.ecomind_backend.quests.domain.model.aggregates.ActivityUser;
import pe.greenminds.ecomind_backend.quests.domain.model.commands.CreateActivityUserCommand;
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

    public ActivityUserCommandServiceImpl(
            ActivityUserRepository activityUserRepository,
            ActivityRepository activityRepository,
            QuestUserRepository questUserRepository
    ) {
        this.activityUserRepository = activityUserRepository;
        this.activityRepository = activityRepository;
        this.questUserRepository = questUserRepository;
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
}
