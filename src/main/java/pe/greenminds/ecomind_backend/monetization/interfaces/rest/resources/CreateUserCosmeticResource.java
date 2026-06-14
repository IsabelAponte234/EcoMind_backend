package pe.greenminds.ecomind_backend.monetization.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Schema(
        name = "CreateUserCosmeticRequest",
        description = "Request payload for creating a new user cosmetic",
        example = """
        {
          "userId": 10,
          "cosmeticId": 5,
          "acquiredAt": "2026-06-14T10:30:00",
          "equipped": false
        }
        """
)
public record CreateUserCosmeticResource(
    @NotNull
    @Schema(description = "User ID", example = "10")
    Long userId,

    @NotNull
    @Schema(description = "Cosmetic ID", example = "5")
    Long cosmeticId,

    @NotNull
    @Schema(description = "Acquisition date", example = "2026-06-14T10:30:00")
    LocalDateTime acquiredAt,

    @NotNull
    @Schema(description = "Whether the cosmetic is equipped", example = "false")
    Boolean equipped
) {
}
