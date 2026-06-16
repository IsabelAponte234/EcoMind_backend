package pe.greenminds.ecomind_backend.profile.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import pe.greenminds.ecomind_backend.profile.domain.model.valueobjects.FamilyRole;
import pe.greenminds.ecomind_backend.profile.domain.model.valueobjects.InvitationStatus;

public record FamilyInvitationResource(
        Long id,
        @JsonProperty("family_id") Long familyId,
        @JsonProperty("inviter_user_id") Long inviterUserId,
        @JsonProperty("invited_user_id") Long invitedUserId,
        @JsonProperty("invited_role") FamilyRole invitedRole,
        InvitationStatus status,
        @JsonProperty("created_at") String createdAt,
        @JsonProperty("responded_at") String respondedAt
) {
}
