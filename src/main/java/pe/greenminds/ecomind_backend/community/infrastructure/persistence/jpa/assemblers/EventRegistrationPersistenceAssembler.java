package pe.greenminds.ecomind_backend.community.infrastructure.persistence.jpa.assemblers;

import pe.greenminds.ecomind_backend.community.domain.model.aggregates.EventRegistration;
import pe.greenminds.ecomind_backend.community.infrastructure.persistence.jpa.entities.EventRegistrationPersistenceEntity;

public class EventRegistrationPersistenceAssembler {
    private EventRegistrationPersistenceAssembler() {}

    public static EventRegistration toDomainFromPersistence(EventRegistrationPersistenceEntity entity) {
        var registration = new EventRegistration(
                entity.getId(),
                entity.getEventId(),
                entity.getUserId(),
                entity.getFamilyId(),
                entity.getRegistrationType(),
                entity.getRegisteredAt(),
                entity.getStatus()
        );
        registration.setId(entity.getId());
        return registration;
    }

    public static EventRegistrationPersistenceEntity toPersistenceFromDomain(EventRegistration registration) {
        var entity = new EventRegistrationPersistenceEntity();

        entity.setId(registration.getId());
        entity.setEventId(registration.getEventId());
        entity.setUserId(registration.getUserId());
        entity.setFamilyId(registration.getFamilyId());
        entity.setRegistrationType(registration.getRegistrationType());
        entity.setRegisteredAt(registration.getRegisteredAt());
        entity.setStatus(registration.getStatus());

        return entity;
    }
}
