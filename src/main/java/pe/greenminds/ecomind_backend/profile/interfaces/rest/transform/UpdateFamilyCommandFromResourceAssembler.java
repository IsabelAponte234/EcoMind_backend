package pe.greenminds.ecomind_backend.profile.interfaces.rest.transform;

import pe.greenminds.ecomind_backend.profile.domain.model.commands.UpdateFamilyCommand;
import pe.greenminds.ecomind_backend.profile.interfaces.rest.resources.UpdateFamilyResource;

public class UpdateFamilyCommandFromResourceAssembler {
    private UpdateFamilyCommandFromResourceAssembler() {}

    public static UpdateFamilyCommand toCommandFromResource(Long familyId, UpdateFamilyResource resource) {
        return new UpdateFamilyCommand(familyId, resource.name(), resource.commitment());
    }
}
