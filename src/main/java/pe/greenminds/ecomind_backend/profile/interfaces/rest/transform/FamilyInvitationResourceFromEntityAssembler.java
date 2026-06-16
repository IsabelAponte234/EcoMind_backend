package pe.greenminds.ecomind_backend.profile.interfaces.rest.transform;

import pe.greenminds.ecomind_backend.profile.domain.model.aggregates.FamilyInvitation;
import pe.greenminds.ecomind_backend.profile.interfaces.rest.resources.FamilyInvitationResource;

public class FamilyInvitationResourceFromEntityAssembler {
    private FamilyInvitationResourceFromEntityAssembler() {}

    public static FamilyInvitationResource toResourceFromEntity(FamilyInvitation invitation) {
        return new FamilyInvitationResource(invitation.getId(), invitation.getFamilyId(), invitation.getInviterUserId(),
                invitation.getInvitedUserId(), invitation.getInvitedRole(), invitation.getStatus(),
                ProfileDateTimeMapper.from(invitation.getCreatedAt()), ProfileDateTimeMapper.from(invitation.getRespondedAt()));
    }
}
