package pe.greenminds.ecomind_backend.community.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@Schema(
        name = "CreatePostRequest",
        description = "Request payload for creating a new community post",
        example = """
        {
          "community_id": 1,
          "user_id": 1,
          "content": "My family and I organized a beach cleanup this weekend. We collected 45 kg of waste.",
          "points": 50,
          "image_url": "https://i.imgur.com/nscRbEZ.jpeg"
        }
        """
)
public record CreatePostResource(
    @NotNull(message = "is required")
    @JsonProperty("community_id")
    @Schema(description = "Community unique identifier", example = "1")
    Long communityId,

    @NotNull(message = "is required")
    @JsonProperty("user_id")
    @Schema(description = "User unique identifier", example = "1")
    Long userId,

    @NotBlank(message = "is required")
    @Schema(description = "Content of the post", example = "My family and I organized a beach cleanup this weekend. We collected 45 kg of waste.")
    String content,

    @NotNull(message = "is required")
    @PositiveOrZero(message = "must be positive or zero")
    @Schema(description = "Quantity of points gained", example = "50")
    Integer points,

    @JsonProperty("image_url")
    @Schema(description = "Post image url", example = "https://i.imgur.com/nscRbEZ.jpeg", nullable = true)
    String imageUrl
) {
}
