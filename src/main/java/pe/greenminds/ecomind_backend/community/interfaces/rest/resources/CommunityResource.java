package pe.greenminds.ecomind_backend.community.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        name = "CommunityResponse",
        description = "Community information response",
        example = """
        {
          "id": 1,
          "name": "EcoMind Lima",
          "user_count": 9,
          "location": "Lima, Peru"
        }
        """
)
public record CommunityResource(
        @Schema(description = "Community unique identifier", example = "1")
        Long id,

        @Schema(description = "Community name", example = "Ecomind Lima")
        String name,

        @JsonProperty("user_count")
        @Schema(description = "Amount of people on the community", example = "9")
        Integer userCount,

        @Schema(description = "Location name", example = "Lima, Peru")
        String location
        ) {



}
