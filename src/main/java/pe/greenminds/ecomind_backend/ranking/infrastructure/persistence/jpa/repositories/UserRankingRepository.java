package pe.greenminds.ecomind_backend.ranking.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.greenminds.ecomind_backend.ranking.infrastructure.persistence.jpa.entities.UserRankingEntity;

import java.util.List;

@Repository
public interface UserRankingRepository extends JpaRepository<UserRankingEntity, Long> {
    List<UserRankingEntity> findByRankingId(Long rankingId);
    List<UserRankingEntity> findByUserId(Long userId);
}
