package pe.greenminds.ecomind_backend.community.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.greenminds.ecomind_backend.community.infrastructure.persistence.jpa.entities.EventPersistenceEntity;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EventPersistenceRepository extends JpaRepository<EventPersistenceEntity, Long> {

    @Query("""
        SELECT e FROM EventPersistenceEntity e
        WHERE (:communityId IS NULL OR e.communityId = :communityId)
          AND (:authorId IS NULL OR e.authorId = :authorId)
          AND (:name IS NULL OR LOWER(e.name) LIKE LOWER(CONCAT('%', :name, '%')))
          AND (:date IS NULL OR e.date = :date)
          AND (:location IS NULL OR LOWER(e.location) LIKE LOWER(CONCAT('%', :location, '%')))
    """)
    List<EventPersistenceEntity> search(
            @Param("communityId") Long communityId,
            @Param("authorId") Long authorId,
            @Param("name") String name,
            @Param("date") LocalDate date,
            @Param("location") String location
    );
}