package pe.greenminds.ecomind_backend.community.domain.model.events;

import pe.greenminds.ecomind_backend.community.domain.model.aggregates.Event;

import java.time.LocalDateTime;

public record EventCreatedEvent(
        Long eventId,
        Long communityId,
        Long authorId,
        String name,
        LocalDateTime occurredOn
) {
    public static EventCreatedEvent from(Event event) {
        return new EventCreatedEvent(
                event.getId(),
                event.getCommunityId(),
                event.getAuthorId(),
                event.getName(),
                LocalDateTime.now()
        );
    }
}