package pe.greenminds.ecomind_backend.profile.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserResource(
        Long id,
        @JsonProperty("community_id") Long communityId,
        String email,
        @JsonProperty("birth_date") String birthDate,
        String name,
        Integer streak,
        String commitment,
        @JsonProperty("registered_at") String registeredAt,
        @JsonProperty("gem_balance") Integer gemBalance,
        Integer ecopoints,
        @JsonProperty("last_streak_date") String lastStreakDate
) {
}
