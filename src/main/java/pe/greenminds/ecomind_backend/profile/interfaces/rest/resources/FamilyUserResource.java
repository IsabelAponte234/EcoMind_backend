package pe.greenminds.ecomind_backend.profile.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import pe.greenminds.ecomind_backend.profile.domain.model.valueobjects.FamilyRole;

public record FamilyUserResource(
        Long id,
        @JsonProperty("user_id") Long userId,
        @JsonProperty("family_id") Long familyId,
        @JsonProperty("family_role") FamilyRole familyRole,
        @JsonProperty("joined_at") String joinedAt
) {
}
