package pe.greenminds.ecomind_backend.community.interfaces.rest.transform;

import pe.greenminds.ecomind_backend.community.domain.model.commands.CreateCommunityCommand;
import pe.greenminds.ecomind_backend.community.interfaces.rest.resources.CreateCommunityResource;

public class CreateCommunityCommandFromResourceAssembler {
    private CreateCommunityCommandFromResourceAssembler() {}

    public static CreateCommunityCommand toCommandFromResource(CreateCommunityResource resource) {
        return new CreateCommunityCommand(
                resource.name(),
                resource.location()
        );
    }
}
