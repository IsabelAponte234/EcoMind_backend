package pe.greenminds.ecomind_backend.community.domain.model.aggregates;

import lombok.Getter;
import lombok.Setter;
import pe.greenminds.ecomind_backend.community.domain.model.events.EventRegistrationCancelledEvent;
import pe.greenminds.ecomind_backend.community.domain.model.events.EventRegistrationCreatedEvent;
import pe.greenminds.ecomind_backend.community.domain.model.valueobjects.EventRegistrationStatus;
import pe.greenminds.ecomind_backend.community.domain.model.valueobjects.EventRegistrationType;
import pe.greenminds.ecomind_backend.shared.domain.model.aggregates.AbstractDomainAggregateRoot;

import java.time.LocalDateTime;
import java.util.Objects;

public class EventRegistration extends AbstractDomainAggregateRoot<EventRegistration> {
    @Getter
    @Setter
    private Long id;

    private Long eventId;
    private Long userId;
    private Long familyId;
    private EventRegistrationType registrationType;
    private LocalDateTime registeredAt;
    private EventRegistrationStatus status;

    public EventRegistration(Long id, Long eventId, Long userId, Long familyId, EventRegistrationType registrationType, LocalDateTime registeredAt, EventRegistrationStatus status) {
        this.id = id;
        this.eventId = Objects.requireNonNull(eventId, "eventId must not be null");
        this.userId = Objects.requireNonNull(userId, "userId must not be null");
        this.registrationType = Objects.requireNonNull(registrationType, "registrationType must not be null");
        this.registeredAt = Objects.requireNonNull(registeredAt, "registeredAt must not be null");
        this.status = Objects.requireNonNull(status, "status must not be null");

        if (registrationType == EventRegistrationType.FAMILY) {
            this.familyId = Objects.requireNonNull(familyId, "familyId must not be null for FAMILY registration");
        } else {
            this.familyId = familyId;
        }
    }

    public EventRegistration(Long eventId, Long userId, Long familyId, EventRegistrationType registrationType) {
        this(null, eventId, userId, familyId, registrationType, LocalDateTime.now(), EventRegistrationStatus.REGISTERED);
    }

    public Long getEventId() { return eventId; }
    public Long getUserId() { return userId; }
    public Long getFamilyId() { return familyId; }
    public EventRegistrationType getRegistrationType() { return registrationType; }
    public LocalDateTime getRegisteredAt() { return registeredAt; }
    public EventRegistrationStatus getStatus() { return status; }

    public void onCreated() {
        registerDomainEvent(EventRegistrationCreatedEvent.from(this));
    }

    public void cancel() {
        if (this.status == EventRegistrationStatus.CANCELLED) {
            throw new IllegalStateException("Event registration is already cancelled");
        }

        this.status = EventRegistrationStatus.CANCELLED;
        registerDomainEvent(EventRegistrationCancelledEvent.from(this));
    }
}
