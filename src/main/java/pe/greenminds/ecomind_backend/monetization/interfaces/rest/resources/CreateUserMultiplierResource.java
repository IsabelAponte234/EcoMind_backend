package pe.greenminds.ecomind_backend.monetization.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Schema(
        name = "CreateUserMultiplierRequest",
        description = "Request payload for activating a multiplier for a user",
        example = """
        {
          "userId": 10,
          "multiplierId": 2,
          "startDate": "2026-07-01T10:00:00",
          "endDate": "2026-07-01T10:30:00"
        }
        """
)
public record CreateUserMultiplierResource(
    @NotNull
    @Schema(description = "User ID", example = "10")
    Long userId,

    @NotNull
    @Schema(description = "Multiplier ID", example = "2")
    Long multiplierId,

    @NotNull
    @Schema(description = "Activation start date", example = "2026-07-01T10:00:00")
    LocalDateTime startDate,

    @NotNull
    @Schema(description = "Activation end date", example = "2026-07-01T10:30:00")
    LocalDateTime endDate
) {
}
