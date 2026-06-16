package pe.greenminds.ecomind_backend.profile.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import pe.greenminds.ecomind_backend.profile.domain.model.valueobjects.FamilyRole;

public record CreateFamilyUserResource(
        @JsonProperty("user_id") @NotNull Long userId,
        @JsonProperty("family_id") @NotNull Long familyId,
        @JsonProperty("family_role") FamilyRole familyRole,
        @JsonProperty("joined_at") String joinedAt
) {
}
