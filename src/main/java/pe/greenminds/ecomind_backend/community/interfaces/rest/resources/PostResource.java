package pe.greenminds.ecomind_backend.community.interfaces.rest.resources;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(
        name = "PostResponse",
        description = "Community post information response",
        example = """
        {
            "id": 1,
            "community_id": 1,
            "user_id": 1,
            "content": "My family and I organized a beach cleanup this weekend. We collected 45 kg of waste.",
            "points": 50,
            "likes": 70,
            "image_url": "https://i.imgur.com/nscRbEZ.jpeg",
            "created_at": "2026-05-09T09:00:00"
        }
        """
)
public record PostResource(
        @Schema(description = "Post unique identifier", example = "1")
        Long id,

        @JsonProperty("community_id")
        @Schema(description = "Community unique identifier", example = "1")
        Long communityId,

        @JsonProperty("user_id")
        @Schema(description = "User unique identifier", example = "1")
        Long userId,

        @Schema(description = "Content of the post", example = "My family and I organized a beach cleanup this weekend. We collected 45 kg of waste.")
        String content,

        @Schema(description = "Quantity of points gained", example = "50")
        Integer points,

        @Schema(description = "Quantity of likes", example = "70")
        Integer likes,

        @JsonProperty("image_url")
        @Schema(description = "Post image url", example = "https://i.imgur.com/nscRbEZ.jpeg", nullable = true)
        String imageUrl,

        @JsonProperty("created_at")
        @Schema(description = "Date of publish", example = "2026-05-09T09:00:00")
        LocalDateTime createdAt
) {
}
