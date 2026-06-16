package pe.greenminds.ecomind_backend.profile.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import pe.greenminds.ecomind_backend.profile.domain.model.valueobjects.FamilyRole;
import pe.greenminds.ecomind_backend.profile.domain.model.valueobjects.InvitationStatus;

public record CreateFamilyInvitationResource(
        @JsonProperty("family_id") @NotNull Long familyId,
        @JsonProperty("inviter_user_id") @NotNull Long inviterUserId,
        @JsonProperty("invited_user_id") @NotNull Long invitedUserId,
        @JsonProperty("invited_role") FamilyRole invitedRole,
        InvitationStatus status,
        @JsonProperty("created_at") String createdAt,
        @JsonProperty("responded_at") String respondedAt
) {
}
