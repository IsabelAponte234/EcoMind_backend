package pe.greenminds.ecomind_backend.community.domain.repositories;

import pe.greenminds.ecomind_backend.community.domain.model.aggregates.EventRegistration;
import pe.greenminds.ecomind_backend.community.domain.model.valueobjects.EventRegistrationStatus;

import java.util.List;
import java.util.Optional;

public interface EventRegistrationRepository {
    Optional<EventRegistration> findById(Long id);

    List<EventRegistration> search(
            Long eventId,
            Long userId,
            Long familyId,
            EventRegistrationStatus status
    );

    Optional<EventRegistration> findByEventIdAndUserId(Long eventId, Long userId);

    boolean existsActiveByEventIdAndUserId(Long eventId, Long userId);

    EventRegistration save(EventRegistration registration);
}
