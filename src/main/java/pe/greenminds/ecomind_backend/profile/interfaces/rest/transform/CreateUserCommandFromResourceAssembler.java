package pe.greenminds.ecomind_backend.profile.interfaces.rest.transform;

import pe.greenminds.ecomind_backend.profile.domain.model.commands.CreateUserCommand;
import pe.greenminds.ecomind_backend.profile.interfaces.rest.resources.CreateUserResource;

public class CreateUserCommandFromResourceAssembler {
    private CreateUserCommandFromResourceAssembler() {}

    public static CreateUserCommand toCommandFromResource(CreateUserResource resource) {
        return new CreateUserCommand(resource.communityId(), resource.email(),
                ProfileDateTimeMapper.toLocalDate(resource.birthDate()), resource.name(), resource.streak(),
                resource.commitment(), ProfileDateTimeMapper.toOffsetDateTime(resource.registeredAt()),
                resource.gemBalance(), resource.ecopoints(), ProfileDateTimeMapper.toLocalDate(resource.lastStreakDate()));
    }
}
