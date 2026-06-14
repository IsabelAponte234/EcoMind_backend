package pe.greenminds.ecomind_backend.quests.interfaces.rest.transform;

import pe.greenminds.ecomind_backend.quests.domain.model.commands.UpdateActivityCommand;
import pe.greenminds.ecomind_backend.quests.interfaces.rest.resources.UpdateActivityResource;

public final class UpdateActivityCommandFromResourceAssembler {
    private UpdateActivityCommandFromResourceAssembler() {
    }

    public static UpdateActivityCommand toCommandFromResource(
            Long activityId,
            UpdateActivityResource resource
    ) {
        return new UpdateActivityCommand(
                activityId,
                resource.description(),
                resource.order(),
                resource.type(),
                resource.activityConfiguration(),
                resource.image()
        );
    }
}
