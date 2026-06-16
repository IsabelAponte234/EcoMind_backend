package pe.greenminds.ecomind_backend.ranking.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;

public record CreateRankingResource(
        @NotBlank String name,
        @NotBlank String type
) {}
