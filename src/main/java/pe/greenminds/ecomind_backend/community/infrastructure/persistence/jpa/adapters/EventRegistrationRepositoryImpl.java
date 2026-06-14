package pe.greenminds.ecomind_backend.community.infrastructure.persistence.jpa.adapters;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Repository;
import pe.greenminds.ecomind_backend.community.domain.model.aggregates.EventRegistration;
import pe.greenminds.ecomind_backend.community.domain.model.valueobjects.EventRegistrationStatus;
import pe.greenminds.ecomind_backend.community.domain.repositories.EventRegistrationRepository;
import pe.greenminds.ecomind_backend.community.infrastructure.persistence.jpa.assemblers.EventRegistrationPersistenceAssembler;
import pe.greenminds.ecomind_backend.community.infrastructure.persistence.jpa.repositories.EventRegistrationPersistenceRepository;

import java.util.List;
import java.util.Optional;

@Repository
public class EventRegistrationRepositoryImpl implements EventRegistrationRepository {

    private final EventRegistrationPersistenceRepository eventRegistrationPersistenceRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    public EventRegistrationRepositoryImpl(EventRegistrationPersistenceRepository eventRegistrationPersistenceRepository, ApplicationEventPublisher applicationEventPublisher) {
        this.eventRegistrationPersistenceRepository = eventRegistrationPersistenceRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public Optional<EventRegistration> findById(Long id) {
        return eventRegistrationPersistenceRepository.findById(id)
                .map(EventRegistrationPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<EventRegistration> search(Long eventId, Long userId, Long familyId, EventRegistrationStatus status) {
        return eventRegistrationPersistenceRepository.search(eventId, userId, familyId, status)
                .stream()
                .map(EventRegistrationPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public Optional<EventRegistration> findByEventIdAndUserId(Long eventId, Long userId) {
        return eventRegistrationPersistenceRepository.findByEventIdAndUserId(eventId, userId)
                .map(EventRegistrationPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public boolean existsActiveByEventIdAndUserId(Long eventId, Long userId) {
        return eventRegistrationPersistenceRepository.existsByEventIdAndUserIdAndStatus(
                eventId,
                userId,
                EventRegistrationStatus.REGISTERED
        );
    }

    @Override
    public EventRegistration save(EventRegistration registration) {
        boolean isNew = registration.getId() == null;

        if (isNew) {
            registration.onCreated();
        }

        var domainEvents = registration.domainEvents();

        var savedEntity = eventRegistrationPersistenceRepository.save(
                EventRegistrationPersistenceAssembler.toPersistenceFromDomain(registration)
        );

        domainEvents.forEach(applicationEventPublisher::publishEvent);
        registration.clearDomainEvents();

        return EventRegistrationPersistenceAssembler.toDomainFromPersistence(savedEntity);
    }
}