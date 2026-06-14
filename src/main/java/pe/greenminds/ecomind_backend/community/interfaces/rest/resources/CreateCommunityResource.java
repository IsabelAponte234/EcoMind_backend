package pe.greenminds.ecomind_backend.community.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(
        name = "CreateCommunityRequest",
        description = "Request payload for creating a new community",
        example = """
        {
          "id": 1,
          "name": "EcoMind Lima",
          "user_count": 9,
          "location": "Lima, Peru"
        }
        """
)
public record CreateCommunityResource(
        @NotNull(message = "is required")
        @Schema(description = "Community name", example = "Ecomind Lima")
        String name,

        @NotNull(message = "is required")
        @Schema(description = "Location name", example = "Lima, Peru")
        String location
) {

}
