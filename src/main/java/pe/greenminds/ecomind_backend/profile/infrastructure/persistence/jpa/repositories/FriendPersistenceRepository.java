package pe.greenminds.ecomind_backend.profile.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.greenminds.ecomind_backend.profile.domain.model.valueobjects.FriendStatus;
import pe.greenminds.ecomind_backend.profile.infrastructure.persistence.jpa.entities.FriendPersistenceEntity;

import java.util.List;

@Repository
public interface FriendPersistenceRepository extends JpaRepository<FriendPersistenceEntity, Long> {
    @Query("""
            SELECT f FROM FriendPersistenceEntity f
            WHERE (:userId IS NULL OR f.userId = :userId OR f.friendId = :userId)
              AND (:status IS NULL OR f.status = :status)
            """)
    List<FriendPersistenceEntity> search(
            @Param("userId") Long userId,
            @Param("status") FriendStatus status
    );

    @Query("""
            SELECT COUNT(f) > 0 FROM FriendPersistenceEntity f
            WHERE (f.userId = :userId AND f.friendId = :friendId)
               OR (f.userId = :friendId AND f.friendId = :userId)
            """)
    boolean existsRelationship(@Param("userId") Long userId, @Param("friendId") Long friendId);

    void deleteByUserIdOrFriendId(Long userId, Long friendId);
}
