package pe.greenminds.ecomind_backend.ranking.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateRankingResource(
        @NotBlank String name,
        @NotBlank String type,
        boolean status
) {}
