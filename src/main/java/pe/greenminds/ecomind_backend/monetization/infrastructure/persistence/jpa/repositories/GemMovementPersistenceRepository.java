package pe.greenminds.ecomind_backend.monetization.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.greenminds.ecomind_backend.monetization.infrastructure.persistence.jpa.entities.GemMovementPersistenceEntity;

@Repository
public interface GemMovementPersistenceRepository extends JpaRepository<GemMovementPersistenceEntity, Long> {
}
