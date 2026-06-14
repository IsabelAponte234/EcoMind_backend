package pe.greenminds.ecomind_backend.monetization.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(
        name = "UserCosmeticResponse",
        description = "User cosmetic information response",
        example = """
        {
          "id": 1,
          "userId": 10,
          "cosmeticId": 5,
          "acquiredAt": "2026-06-14T10:30:00",
          "equipped": true
        }
        """
)
public record UserCosmeticResource(
    @Schema(description = "User cosmetic unique identifier", example = "1")
    Long id,

    @Schema(description = "User ID", example = "10")
    Long userId,

    @Schema(description = "Cosmetic ID", example = "5")
    Long cosmeticId,

    @Schema(description = "Acquisition date", example = "2026-06-14T10:30:00")
    LocalDateTime acquiredAt,

    @Schema(description = "Whether the cosmetic is equipped", example = "true")
    Boolean equipped
) {
}
