package pe.greenminds.ecomind_backend.profile.interfaces.rest.transform;

import pe.greenminds.ecomind_backend.profile.domain.model.commands.UpdateFamilyUserCommand;
import pe.greenminds.ecomind_backend.profile.interfaces.rest.resources.UpdateFamilyUserResource;

public class UpdateFamilyUserCommandFromResourceAssembler {
    private UpdateFamilyUserCommandFromResourceAssembler() {}

    public static UpdateFamilyUserCommand toCommandFromResource(Long familyUserId, UpdateFamilyUserResource resource) {
        return new UpdateFamilyUserCommand(familyUserId, resource.familyRole(),
                ProfileDateTimeMapper.toOffsetDateTime(resource.joinedAt()));
    }
}
