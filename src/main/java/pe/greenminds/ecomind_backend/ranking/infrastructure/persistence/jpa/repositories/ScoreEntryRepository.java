package pe.greenminds.ecomind_backend.ranking.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.greenminds.ecomind_backend.ranking.infrastructure.persistence.jpa.entities.ScoreEntryEntity;

import java.util.List;

@Repository
public interface ScoreEntryRepository extends JpaRepository<ScoreEntryEntity, Long> {
    List<ScoreEntryEntity> findByUserId(Long userId);
}
