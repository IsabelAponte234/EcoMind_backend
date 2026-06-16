package pe.greenminds.ecomind_backend.profile.interfaces.rest.transform;

import pe.greenminds.ecomind_backend.profile.domain.model.commands.CreateFamilyInvitationCommand;
import pe.greenminds.ecomind_backend.profile.interfaces.rest.resources.CreateFamilyInvitationResource;

public class CreateFamilyInvitationCommandFromResourceAssembler {
    private CreateFamilyInvitationCommandFromResourceAssembler() {}

    public static CreateFamilyInvitationCommand toCommandFromResource(CreateFamilyInvitationResource resource) {
        return new CreateFamilyInvitationCommand(resource.familyId(), resource.inviterUserId(), resource.invitedUserId(),
                resource.invitedRole(), resource.status(), ProfileDateTimeMapper.toOffsetDateTime(resource.createdAt()),
                ProfileDateTimeMapper.toOffsetDateTime(resource.respondedAt()));
    }
}
