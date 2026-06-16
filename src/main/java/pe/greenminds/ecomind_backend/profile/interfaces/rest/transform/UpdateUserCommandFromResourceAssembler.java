package pe.greenminds.ecomind_backend.profile.interfaces.rest.transform;

import pe.greenminds.ecomind_backend.profile.domain.model.commands.UpdateUserCommand;
import pe.greenminds.ecomind_backend.profile.interfaces.rest.resources.UpdateUserResource;

public class UpdateUserCommandFromResourceAssembler {
    private UpdateUserCommandFromResourceAssembler() {}

    public static UpdateUserCommand toCommandFromResource(Long userId, UpdateUserResource resource) {
        return new UpdateUserCommand(userId, resource.communityId(), resource.email(),
                ProfileDateTimeMapper.toLocalDate(resource.birthDate()), resource.name(), resource.streak(),
                resource.commitment(), ProfileDateTimeMapper.toOffsetDateTime(resource.registeredAt()),
                resource.gemBalance(), resource.ecopoints(), ProfileDateTimeMapper.toLocalDate(resource.lastStreakDate()));
    }
}
