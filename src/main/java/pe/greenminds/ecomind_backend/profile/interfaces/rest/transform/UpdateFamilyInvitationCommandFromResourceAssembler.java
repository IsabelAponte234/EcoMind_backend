package pe.greenminds.ecomind_backend.profile.interfaces.rest.transform;

import pe.greenminds.ecomind_backend.profile.domain.model.commands.UpdateFamilyInvitationCommand;
import pe.greenminds.ecomind_backend.profile.interfaces.rest.resources.UpdateFamilyInvitationResource;

public class UpdateFamilyInvitationCommandFromResourceAssembler {
    private UpdateFamilyInvitationCommandFromResourceAssembler() {}

    public static UpdateFamilyInvitationCommand toCommandFromResource(Long invitationId, UpdateFamilyInvitationResource resource) {
        return new UpdateFamilyInvitationCommand(invitationId, resource.familyId(), resource.inviterUserId(),
                resource.invitedUserId(), resource.invitedRole(), resource.status(),
                ProfileDateTimeMapper.toOffsetDateTime(resource.createdAt()),
                ProfileDateTimeMapper.toOffsetDateTime(resource.respondedAt()));
    }
}
