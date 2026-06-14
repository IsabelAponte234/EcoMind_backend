package pe.greenminds.ecomind_backend.community.domain.model.events;

import pe.greenminds.ecomind_backend.community.domain.model.aggregates.EventRegistration;
import pe.greenminds.ecomind_backend.community.domain.model.valueobjects.EventRegistrationStatus;
import pe.greenminds.ecomind_backend.community.domain.model.valueobjects.EventRegistrationType;

import java.time.LocalDateTime;

public record EventRegistrationCreatedEvent(
        Long eventRegistrationId,
        Long eventId,
        Long userId,
        Long familyId,
        EventRegistrationType registrationType,
        EventRegistrationStatus status,
        LocalDateTime registeredAt,
        LocalDateTime occurredOn
) {
    public static EventRegistrationCreatedEvent from(EventRegistration eventRegistration){
        return new EventRegistrationCreatedEvent(
                eventRegistration.getId(),
                eventRegistration.getEventId(),
                eventRegistration.getUserId(),
                eventRegistration.getFamilyId(),
                eventRegistration.getRegistrationType(),
                eventRegistration.getStatus(),
                eventRegistration.getRegisteredAt(),
                LocalDateTime.now()
        );
    }

}
