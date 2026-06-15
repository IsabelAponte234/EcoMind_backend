package pe.greenminds.ecomind_backend.community.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.greenminds.ecomind_backend.community.domain.model.valueobjects.EventRegistrationStatus;
import pe.greenminds.ecomind_backend.community.infrastructure.persistence.jpa.entities.EventRegistrationPersistenceEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRegistrationPersistenceRepository extends JpaRepository<EventRegistrationPersistenceEntity, Long> {
    @Query("""
        SELECT r FROM EventRegistrationPersistenceEntity r
        WHERE (:eventId IS NULL OR r.eventId = :eventId)
          AND (:userId IS NULL OR r.userId = :userId)
          AND (:familyId IS NULL OR r.familyId = :familyId)
          AND (:status IS NULL OR r.status = :status)
    """)
    List<EventRegistrationPersistenceEntity> search(
            @Param("eventId") Long eventId,
            @Param("userId") Long userId,
            @Param("familyId") Long familyId,
            @Param("status") EventRegistrationStatus status
    );

    Optional<EventRegistrationPersistenceEntity> findByEventIdAndUserId(
            Long eventId,
            Long userId
    );

    boolean existsByEventIdAndUserIdAndStatus(
            Long eventId,
            Long userId,
            EventRegistrationStatus status
    );
}
