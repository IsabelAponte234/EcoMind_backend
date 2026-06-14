package pe.greenminds.ecomind_backend.community.infrastructure.persistence.jpa.assemblers;

import pe.greenminds.ecomind_backend.community.domain.model.aggregates.Event;
import pe.greenminds.ecomind_backend.community.infrastructure.persistence.jpa.entities.EventPersistenceEntity;

public class EventPersistenceAssembler {
    private EventPersistenceAssembler() {}

    public static Event toDomainFromPersistence(EventPersistenceEntity entity) {
        var event = new Event(
                entity.getCommunityId(),
                entity.getAuthorId(),
                entity.getName(),
                entity.getDescription(),
                entity.getDate(),
                entity.getStartTime(),
                entity.getEndTime(),
                entity.getLocation(),
                entity.getLatitude(),
                entity.getLongitude(),
                entity.getCapacity(),
                entity.getImageUrl()
        );

        event.setId(entity.getId());
        return event;
    }

    public static EventPersistenceEntity toPersistenceFromDomain(Event event) {
        var entity = new EventPersistenceEntity();

        entity.setId(event.getId());
        entity.setCommunityId(event.getCommunityId());
        entity.setAuthorId(event.getAuthorId());
        entity.setName(event.getName());
        entity.setDescription(event.getDescription());
        entity.setDate(event.getDate());
        entity.setStartTime(event.getStartTime());
        entity.setEndTime(event.getEndTime());
        entity.setLocation(event.getLocation());
        entity.setLatitude(event.getLatitude());
        entity.setLongitude(event.getLongitude());
        entity.setCapacity(event.getCapacity());
        entity.setImageUrl(event.getImageUrl());

        return entity;
    }
}