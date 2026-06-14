package pe.greenminds.ecomind_backend.monetization.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        name = "GemMovementResponse",
        description = "Gem movement information response",
        example = """
        {
          "id": 1,
          "userId": 10,
          "type": "EARN",
          "amount": 50,
          "origin": "QUEST",
          "originId": 3
        }
        """
)
public record GemMovementResource(
    @Schema(description = "Gem movement unique identifier", example = "1")
    Long id,

    @Schema(description = "User ID", example = "10")
    Long userId,

    @Schema(description = "Movement type", example = "EARN")
    String type,

    @Schema(description = "Amount of gems", example = "50")
    Integer amount,

    @Schema(description = "Origin of the movement", example = "QUEST")
    String origin,

    @Schema(description = "Origin entity ID", example = "3")
    Long originId
) {
}
