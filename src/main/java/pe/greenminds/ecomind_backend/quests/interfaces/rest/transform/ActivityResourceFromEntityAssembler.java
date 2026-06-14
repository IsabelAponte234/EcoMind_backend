package pe.greenminds.ecomind_backend.quests.interfaces.rest.transform;

import pe.greenminds.ecomind_backend.quests.domain.model.aggregates.Activity;
import pe.greenminds.ecomind_backend.quests.interfaces.rest.resources.ActivityResource;

public class ActivityResourceFromEntityAssembler {
    private ActivityResourceFromEntityAssembler(){}

    public static ActivityResource toResourceFromEntity(Activity activity) {
        return new ActivityResource(
                activity.getId(),
                activity.getQuestId(),
                activity.getDescription(),
                activity.getOrder(),
                activity.getActivityType(),
                activity.getActivityConfiguration(),
                activity.getImage()
        );
    }
}
