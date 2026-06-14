package pe.greenminds.ecomind_backend.monetization.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(
        name = "CreateGemMovementRequest",
        description = "Request payload for creating a new gem movement",
        example = """
        {
          "userId": 10,
          "type": "EARN",
          "amount": 50,
          "origin": "QUEST",
          "originId": 3
        }
        """
)
public record CreateGemMovementResource(
    @NotNull
    @Schema(description = "User ID", example = "10")
    Long userId,

    @NotBlank(message = "is required")
    @Schema(description = "Movement type", example = "EARN")
    String type,

    @NotNull
    @Schema(description = "Amount of gems", example = "50")
    Integer amount,

    @NotBlank(message = "is required")
    @Schema(description = "Origin of the movement", example = "QUEST")
    String origin,

    @Schema(description = "Origin entity ID", example = "3")
    Long originId
) {
}
