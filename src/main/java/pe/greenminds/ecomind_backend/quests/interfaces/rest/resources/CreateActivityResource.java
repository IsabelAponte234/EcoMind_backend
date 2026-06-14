package pe.greenminds.ecomind_backend.quests.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import pe.greenminds.ecomind_backend.quests.domain.model.valueobjects.ActivityType;

@Schema(
        name = "CreateActivityRequest",
        description = """
                Request payload for creating a new activity. The activity is automatically
                assigned to users who have the related quest in progress, but not to users
                who have already completed it.
                """,
        example = """
        {
              "questId": 1,
              "description": "Check the rooms",
              "order": 1,
              "type": "CHECKBOX",
              "image_url": null
        }
        """
)
public record CreateActivityResource(
        @NotNull
        @Schema(description = "Quest where the activity exists")
        Long questId,

        @Schema(description = "Activity instructions")
        String description,

        @NotNull
        @Positive
        @Schema(description = "Number of order of the activity")
        Integer order,

        @NotNull
        @Schema(description = "Type of activity")
        ActivityType type,

        @Schema(description = "Image url for individual activity")
        String image
) {
}
