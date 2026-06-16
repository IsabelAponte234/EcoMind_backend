package pe.greenminds.ecomind_backend.ranking.interfaces.rest.resources;

import jakarta.validation.constraints.NotNull;

public record CreateUserRankingResource(
        @NotNull Long userId,
        @NotNull Long rankingId,
        int rankPosition,
        int score
) {}
