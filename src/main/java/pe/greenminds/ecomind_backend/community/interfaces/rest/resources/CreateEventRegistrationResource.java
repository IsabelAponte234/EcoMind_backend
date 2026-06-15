package pe.greenminds.ecomind_backend.community.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import pe.greenminds.ecomind_backend.community.domain.model.valueobjects.EventRegistrationType;

@Schema(
        name = "CreateEventRegistrationRequest",
        description = "Request payload for registering a user to a community event",
        example = """
        {
          "user_id": 15,
          "family_id": null,
          "registration_type": "INDIVIDUAL"
        }
        """
)
public record CreateEventRegistrationResource(
        @NotNull(message = "is required")
        @JsonProperty("user_id")
        @Schema(description = "User identifier", example = "15")
        Long userId,

        @JsonProperty("family_id")
        @Schema(description = "Family identifier when the registration is family-based", example = "3", nullable = true)
        Long familyId,

        @NotNull(message = "is required")
        @JsonProperty("registration_type")
        @Schema(description = "Registration type", example = "INDIVIDUAL", allowableValues = {"INDIVIDUAL", "FAMILY"})
        EventRegistrationType registrationType
) {
}