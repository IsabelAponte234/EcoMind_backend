package pe.greenminds.ecomind_backend.profile.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UpdateUserResource(
        @JsonProperty("community_id") Long communityId,
        @Email @NotBlank String email,
        @JsonProperty("birth_date") String birthDate,
        @NotBlank String name,
        Integer streak,
        String commitment,
        @JsonProperty("registered_at") String registeredAt,
        @JsonProperty("gem_balance") Integer gemBalance,
        Integer ecopoints,
        @JsonProperty("last_streak_date") String lastStreakDate
) {
}
