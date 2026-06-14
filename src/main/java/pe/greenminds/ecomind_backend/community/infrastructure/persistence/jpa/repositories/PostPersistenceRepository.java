package pe.greenminds.ecomind_backend.community.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.greenminds.ecomind_backend.community.infrastructure.persistence.jpa.entities.PostPersistenceEntity;

import java.util.List;

@Repository
public interface PostPersistenceRepository extends JpaRepository<PostPersistenceEntity, Long> {

    @Query("""
    SELECT p FROM PostPersistenceEntity p
    WHERE (:communityId IS NULL OR p.communityId = :communityId)
      AND (:userId IS NULL OR p.userId = :userId)
""")
    List<PostPersistenceEntity> search(
            @Param("communityId") Long communityId,
            @Param("userId") Long userId
    );
}