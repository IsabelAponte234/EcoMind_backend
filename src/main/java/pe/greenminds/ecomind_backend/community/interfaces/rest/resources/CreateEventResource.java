package pe.greenminds.ecomind_backend.community.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDate;
import java.time.LocalTime;

@Schema(
        name = "CreateEventRequest",
        description = "Request payload for creating a new community event",
        example = """
        {
          "community_id": 1,
          "author_id": 2,
          "name": "Limpieza del parque",
          "description": "Actividad comunitaria para recolectar residuos.",
          "date": "2026-06-20",
          "start_time": "09:00:00",
          "end_time": "12:00:00",
          "location": "Parque Central",
          "latitude": -12.0464,
          "longitude": -77.0428,
          "capacity": 50,
          "image_url": "https://example.com/event.png"
        }
        """
)
public record CreateEventResource(
        @NotNull(message = "is required")
        @JsonProperty("community_id")
        @Schema(description = "Community identifier", example = "1")
        Long communityId,

        @NotNull(message = "is required")
        @JsonProperty("author_id")
        @Schema(description = "Author user identifier", example = "2")
        Long authorId,

        @NotBlank(message = "is required")
        @Schema(description = "Event name", example = "Limpieza del parque", minLength = 1, maxLength = 100)
        String name,

        @NotBlank(message = "is required")
        @Schema(description = "Event description", example = "Actividad comunitaria para recolectar residuos.", minLength = 1, maxLength = 500)
        String description,

        @NotNull(message = "is required")
        @Schema(description = "Event date", example = "2026-06-20")
        LocalDate date,

        @NotNull(message = "is required")
        @JsonProperty("start_time")
        @Schema(description = "Event start time", example = "09:00:00")
        LocalTime startTime,

        @NotNull(message = "is required")
        @JsonProperty("end_time")
        @Schema(description = "Event end time", example = "12:00:00")
        LocalTime endTime,

        @NotBlank(message = "is required")
        @Schema(description = "Event location", example = "Parque Central", minLength = 1, maxLength = 255)
        String location,

        @Schema(description = "Event latitude", example = "-12.0464")
        Double latitude,

        @Schema(description = "Event longitude", example = "-77.0428")
        Double longitude,

        @PositiveOrZero(message = "must be positive or zero")
        @Schema(description = "Maximum event capacity", example = "50")
        Integer capacity,

        @JsonProperty("image_url")
        @Schema(description = "Event image url", example = "https://example.com/event.png", nullable = true)
        String imageUrl
) {
}