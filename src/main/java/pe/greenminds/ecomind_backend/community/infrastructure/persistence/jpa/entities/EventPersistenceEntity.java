package pe.greenminds.ecomind_backend.community.infrastructure.persistence.jpa.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import pe.greenminds.ecomind_backend.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "community_events")
public class EventPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(name = "community_id", nullable = false)
    private Long communityId;

    @Column(name = "author_id", nullable = false)
    private Long authorId;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "description", nullable = false, length = 500)
    private String description;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

    @Column(name = "location", nullable = false, length = 255)
    private String location;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "capacity")
    private Integer capacity;

    @Column(name = "image_url")
    private String imageUrl;

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

    public void setCommunityId(Long communityId) { this.communityId = communityId; }
    public void setAuthorId(Long authorId) { this.authorId = authorId; }
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setDate(LocalDate date) { this.date = date; }
    public void setStartTime(LocalTime startTime) { this.startTime = startTime; }
    public void setEndTime(LocalTime endTime) { this.endTime = endTime; }
    public void setLocation(String location) { this.location = location; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }
    public void setCapacity(Integer capacity) { this.capacity = capacity; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}
