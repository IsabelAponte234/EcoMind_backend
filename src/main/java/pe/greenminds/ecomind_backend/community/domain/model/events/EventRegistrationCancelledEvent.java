package pe.greenminds.ecomind_backend.community.domain.model.events;

import pe.greenminds.ecomind_backend.community.domain.model.aggregates.EventRegistration;

import java.time.LocalDateTime;

public record EventRegistrationCancelledEvent(
        Long eventRegistrationId,
        Long eventId,
        Long userId,
        LocalDateTime occurredOn
) {
    public static EventRegistrationCancelledEvent from(EventRegistration registration) {
        return new EventRegistrationCancelledEvent(
                registration.getId(),
                registration.getEventId(),
                registration.getUserId(),
                LocalDateTime.now()
        );
    }
}