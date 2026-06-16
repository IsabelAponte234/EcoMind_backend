package pe.greenminds.ecomind_backend.profile.interfaces.rest.transform;

import pe.greenminds.ecomind_backend.profile.domain.model.commands.CreateFamilyUserCommand;
import pe.greenminds.ecomind_backend.profile.interfaces.rest.resources.CreateFamilyUserResource;

public class CreateFamilyUserCommandFromResourceAssembler {
    private CreateFamilyUserCommandFromResourceAssembler() {}

    public static CreateFamilyUserCommand toCommandFromResource(CreateFamilyUserResource resource) {
        return new CreateFamilyUserCommand(resource.userId(), resource.familyId(), resource.familyRole(),
                ProfileDateTimeMapper.toOffsetDateTime(resource.joinedAt()));
    }
}
