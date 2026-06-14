package pe.greenminds.ecomind_backend.community.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.greenminds.ecomind_backend.community.infrastructure.persistence.jpa.entities.CommunityPersistenceEntity;

import java.util.List;

@Repository
public interface CommunityPersistenceRepository extends JpaRepository<CommunityPersistenceEntity, Long> {
    @Query("""
    SELECT c FROM CommunityPersistenceEntity c
    WHERE (:name IS NULL
           OR LOWER(c.name) LIKE LOWER(CONCAT('%', CAST(:name AS string), '%')))
      AND (:location IS NULL
           OR LOWER(c.location) LIKE LOWER(CONCAT('%', CAST(:location AS string), '%')))
""")
    List<CommunityPersistenceEntity> search(
            @Param("name") String name,
            @Param("location") String location
    );
}