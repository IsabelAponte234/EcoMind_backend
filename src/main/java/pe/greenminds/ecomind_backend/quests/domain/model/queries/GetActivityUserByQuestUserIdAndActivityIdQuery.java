package pe.greenminds.ecomind_backend.quests.domain.model.queries;

public record GetActivityUserByQuestUserIdAndActivityIdQuery(
        Long questUserId,
        Long activityId
) {
}
