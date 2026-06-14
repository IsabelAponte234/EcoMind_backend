package pe.greenminds.ecomind_backend.ranking.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateScoreEntryResource(
        @NotNull Long userId,
        @NotNull int score,
        @NotBlank String entryType,
        String description
) {}
