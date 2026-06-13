package pe.greenminds.ecomind_backend.community.domain.repositories;

import pe.greenminds.ecomind_backend.community.domain.model.aggregates.Event;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface EventRepository {
    Optional<Event> findById(Long id);

    List<Event> search(
            Long communityId,
            Long authorId,
            String name,
            LocalDate date,
            String location
    );

    Event save(Event event);

    void deleteById(Long id);

    boolean existsById(Long id);
}