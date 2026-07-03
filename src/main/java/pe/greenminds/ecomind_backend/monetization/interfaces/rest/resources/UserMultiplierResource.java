package pe.greenminds.ecomind_backend.monetization.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(
        name = "UserMultiplierResponse",
        description = "User multiplier information response",
        example = """
        {
          "id": 1,
          "userId": 10,
          "multiplierId": 2,
          "startDate": "2026-07-01T10:00:00",
          "endDate": "2026-07-01T10:30:00"
        }
        """
)
public record UserMultiplierResource(
    @Schema(description = "User multiplier unique identifier", example = "1")
    Long id,

    @Schema(description = "User ID", example = "10")
    Long userId,

    @Schema(description = "Multiplier ID", example = "2")
    Long multiplierId,

    @Schema(description = "Activation start date", example = "2026-07-01T10:00:00")
    LocalDateTime startDate,

    @Schema(description = "Activation end date", example = "2026-07-01T10:30:00")
    LocalDateTime endDate
) {
}
