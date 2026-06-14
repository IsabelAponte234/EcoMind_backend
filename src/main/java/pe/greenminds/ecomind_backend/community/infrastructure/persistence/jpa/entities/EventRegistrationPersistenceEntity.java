package pe.greenminds.ecomind_backend.community.infrastructure.persistence.jpa.entities;

import jakarta.persistence.*;
import pe.greenminds.ecomind_backend.community.domain.model.valueobjects.EventRegistrationStatus;
import pe.greenminds.ecomind_backend.community.domain.model.valueobjects.EventRegistrationType;
import pe.greenminds.ecomind_backend.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;

import java.time.LocalDateTime;

@Entity
@Table(name = "community_event_registrations")
public class EventRegistrationPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(name = "event_id", nullable = false)
    private Long eventId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "family_id")
    private Long familyId;

    @Enumerated(EnumType.STRING)
    @Column(name = "registration_type", nullable = false)
    private EventRegistrationType registrationType;

    @Column(name = "registered_at", nullable = false)
    private LocalDateTime registeredAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private EventRegistrationStatus status;

    public Long getEventId() { return eventId; }
    public Long getUserId() { return userId; }
    public Long getFamilyId() { return familyId; }
    public EventRegistrationType getRegistrationType() { return registrationType; }
    public LocalDateTime getRegisteredAt() { return registeredAt; }
    public EventRegistrationStatus getStatus() { return status; }

    public void setEventId(Long eventId) { this.eventId = eventId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public void setFamilyId(Long familyId) { this.familyId = familyId; }
    public void setRegistrationType(EventRegistrationType registrationType) { this.registrationType = registrationType; }
    public void setRegisteredAt(LocalDateTime registeredAt) { this.registeredAt = registeredAt; }
    public void setStatus(EventRegistrationStatus status) { this.status = status; }
}
