package pe.greenminds.ecomind_backend.quests.interfaces.rest.transform;

import pe.greenminds.ecomind_backend.quests.domain.model.commands.CreateActivityCommand;
import pe.greenminds.ecomind_backend.quests.interfaces.rest.resources.CreateActivityResource;

public class CreateActivityCommandFromResourceAssembler {
    private CreateActivityCommandFromResourceAssembler() {}

    public static CreateActivityCommand toCommandFromResource(
            CreateActivityResource resource
    ){
        return new CreateActivityCommand(
                resource.questId(),
                resource.description(),
                resource.order(),
                resource.type(),
                resource.activityConfiguration(),
                resource.image()
        );
    }
}
