package pe.greenminds.ecomind_backend.community.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import pe.greenminds.ecomind_backend.community.domain.model.valueobjects.EventRegistrationStatus;
import pe.greenminds.ecomind_backend.community.domain.model.valueobjects.EventRegistrationType;

import java.time.LocalDateTime;

@Schema(
        name = "EventRegistrationResponse",
        description = "Community event registration information response",
        example = """
        {
          "id": 1,
          "event_id": 10,
          "user_id": 15,
          "family_id": null,
          "registration_type": "INDIVIDUAL",
          "registered_at": "2026-06-14T10:30:00",
          "status": "REGISTERED"
        }
        """
)
public record EventRegistrationResource(
        @Schema(description = "Event registration unique identifier", example = "1")
        Long id,

        @JsonProperty("event_id")
        @Schema(description = "Event identifier", example = "10")
        Long eventId,

        @JsonProperty("user_id")
        @Schema(description = "User identifier", example = "15")
        Long userId,

        @JsonProperty("family_id")
        @Schema(description = "Family identifier when the registration is family-based", example = "3", nullable = true)
        Long familyId,

        @JsonProperty("registration_type")
        @Schema(description = "Registration type", example = "INDIVIDUAL", allowableValues = {"INDIVIDUAL", "FAMILY"})
        EventRegistrationType registrationType,

        @JsonProperty("registered_at")
        @Schema(description = "Registration date and time", example = "2026-06-14T10:30:00")
        LocalDateTime registeredAt,

        @Schema(description = "Registration status", example = "REGISTERED", allowableValues = {"REGISTERED", "CANCELLED"})
        EventRegistrationStatus status
) {
}