package pe.greenminds.ecomind_backend.community.infrastructure.persistence.jpa.adapters;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Repository;
import pe.greenminds.ecomind_backend.community.domain.model.aggregates.Event;
import pe.greenminds.ecomind_backend.community.domain.repositories.EventRepository;
import pe.greenminds.ecomind_backend.community.infrastructure.persistence.jpa.assemblers.EventPersistenceAssembler;
import pe.greenminds.ecomind_backend.community.infrastructure.persistence.jpa.repositories.EventPersistenceRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public class EventRepositoryImpl implements EventRepository {

    private final EventPersistenceRepository eventPersistenceRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    public EventRepositoryImpl(EventPersistenceRepository eventPersistenceRepository, ApplicationEventPublisher applicationEventPublisher) {
        this.eventPersistenceRepository = eventPersistenceRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public Optional<Event> findById(Long id) {
        return eventPersistenceRepository.findById(id).map(EventPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<Event> search(Long communityId, Long authorId, String name, LocalDate date, String location) {
        var nameFilter = name == null ? "" : name;
        var locationFilter = location == null ? "" : location;

        return eventPersistenceRepository
                .search(communityId, authorId, nameFilter, date, locationFilter)
                .stream()
                .map(EventPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public Event save(Event event) {
        boolean isNew = event.getId() == null;
        var savedEntity = eventPersistenceRepository.save(EventPersistenceAssembler.toPersistenceFromDomain(event));
        var savedEvent = EventPersistenceAssembler.toDomainFromPersistence(savedEntity);
        if (isNew) {
            savedEvent.onCreated();
            savedEvent.domainEvents().forEach(applicationEventPublisher::publishEvent);
            savedEvent.clearDomainEvents();
        }
        return savedEvent;
    }

    @Override
    public void deleteById(Long id) {eventPersistenceRepository.deleteById(id);}

    @Override
    public boolean existsById(Long id) {return eventPersistenceRepository.existsById(id);}
}
