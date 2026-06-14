package pe.greenminds.ecomind_backend.quests.interfaces.rest.transform;

import pe.greenminds.ecomind_backend.quests.domain.model.aggregates.ActivityUser;
import pe.greenminds.ecomind_backend.quests.interfaces.rest.resources.ActivityUserResource;

public final class ActivityUserResourceFromEntityAssembler {
    private ActivityUserResourceFromEntityAssembler() {
    }

    public static ActivityUserResource toResourceFromEntity(ActivityUser activityUser) {
        return new ActivityUserResource(
                activityUser.getId(),
                activityUser.getQuestUserId(),
                activityUser.getActivityId(),
                activityUser.getProgress(),
                activityUser.getEndDate(),
                activityUser.getActivityDescription(),
                activityUser.getActivityConfiguration(),
                activityUser.getCollaborativeSessionId()
        );
    }
}
