package pe.greenminds.ecomind_backend.quests.domain.model.commands;

public record CreateActivityUserCommand(
        Long questUserId,
        Long activityId,
        Long collaborativeSessionId
) {
}
