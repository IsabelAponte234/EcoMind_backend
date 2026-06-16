package pe.greenminds.ecomind_backend.profile.interfaces.rest.transform;

import pe.greenminds.ecomind_backend.profile.domain.model.commands.CreateFamilyCommand;
import pe.greenminds.ecomind_backend.profile.interfaces.rest.resources.CreateFamilyResource;

public class CreateFamilyCommandFromResourceAssembler {
    private CreateFamilyCommandFromResourceAssembler() {}

    public static CreateFamilyCommand toCommandFromResource(CreateFamilyResource resource) {
        return new CreateFamilyCommand(resource.name(), resource.commitment());
    }
}
