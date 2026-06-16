package pe.greenminds.ecomind_backend.profile.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserStatsResource(
        @JsonProperty("gem_balance") Integer gemBalance,
        Integer ecopoints,
        Integer streak,
        @JsonProperty("last_streak_date") String lastStreakDate
) {
}
