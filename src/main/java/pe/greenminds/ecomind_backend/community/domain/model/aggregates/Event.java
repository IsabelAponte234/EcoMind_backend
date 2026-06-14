package pe.greenminds.ecomind_backend.community.domain.model.aggregates;

import lombok.Getter;
import lombok.Setter;
import pe.greenminds.ecomind_backend.community.domain.model.events.EventCreatedEvent;
import pe.greenminds.ecomind_backend.shared.domain.model.aggregates.AbstractDomainAggregateRoot;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class Event extends AbstractDomainAggregateRoot<Event> {
    @Getter
    @Setter
    private Long id;

    private Long communityId;
    private Long authorId;
    private String name;
    private String description;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private String location;
    private Double latitude;
    private Double longitude;
    private Integer capacity;
    private String imageUrl;

    public Event(Long id, Long communityId, Long authorId, String name, String description, LocalDate date, LocalTime startTime, LocalTime endTime, String location, Double latitude, Double longitude, Integer capacity, String imageUrl) {
        this.id = id;
        this.communityId = Objects.requireNonNull(communityId, "communityId must not be null");
        this.authorId = Objects.requireNonNull(authorId, "authorId must not be null");
        this.name = Objects.requireNonNull(name, "name must not be null");
        this.description = Objects.requireNonNull(description, "description must not be null");
        this.date = Objects.requireNonNull(date, "date must not be null");
        this.startTime = Objects.requireNonNull(startTime, "startTime must not be null");
        this.endTime = Objects.requireNonNull(endTime, "endTime must not be null");
        this.location = Objects.requireNonNull(location, "location must not be null");
        this.latitude = latitude;
        this.longitude = longitude;
        this.capacity = capacity;
        this.imageUrl = imageUrl;
    }

    public Event(Long communityId, Long authorId, String name, String description, LocalDate date, LocalTime startTime, LocalTime endTime, String location, Double latitude, Double longitude, Integer capacity, String imageUrl) {
        this(null, communityId, authorId, name, description, date, startTime, endTime, location, latitude, longitude, capacity, imageUrl);
    }

    public Long getCommunityId() { return communityId; }
    public Long getAuthorId() { return authorId; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public LocalDate getDate() { return date; }
    public LocalTime getStartTime() { return startTime; }
    public LocalTime getEndTime() { return endTime; }
    public String getLocation() { return location; }
    public Double getLatitude() { return latitude; }
    public Double getLongitude() { return longitude; }
    public Integer getCapacity() { return capacity; }
    public String getImageUrl() { return imageUrl; }

    public void onCreated() {
        registerDomainEvent(EventCreatedEvent.from(this));
    }
}
