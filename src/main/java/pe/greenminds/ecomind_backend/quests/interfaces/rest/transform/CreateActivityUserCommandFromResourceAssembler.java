package pe.greenminds.ecomind_backend.quests.interfaces.rest.transform;

import pe.greenminds.ecomind_backend.quests.domain.model.commands.CreateActivityUserCommand;
import pe.greenminds.ecomind_backend.quests.interfaces.rest.resources.CreateActivityUserResource;

public final class CreateActivityUserCommandFromResourceAssembler {
    private CreateActivityUserCommandFromResourceAssembler() {
    }

    public static CreateActivityUserCommand toCommandFromResource(
            CreateActivityUserResource resource
    ) {
        return new CreateActivityUserCommand(
                resource.questUserId(),
                resource.activityId(),
                resource.collaborativeSessionId()
        );
    }
}
