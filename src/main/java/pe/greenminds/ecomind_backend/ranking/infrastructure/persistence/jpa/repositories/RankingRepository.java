package pe.greenminds.ecomind_backend.ranking.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.greenminds.ecomind_backend.ranking.infrastructure.persistence.jpa.entities.RankingEntity;

import java.util.Optional;

@Repository
public interface RankingRepository extends JpaRepository<RankingEntity, Long> {
    boolean existsByName(String name);
    Optional<RankingEntity> findByName(String name);
}
