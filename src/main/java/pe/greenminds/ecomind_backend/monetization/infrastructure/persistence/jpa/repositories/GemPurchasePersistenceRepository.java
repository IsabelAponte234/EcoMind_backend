package pe.greenminds.ecomind_backend.monetization.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.greenminds.ecomind_backend.monetization.infrastructure.persistence.jpa.entities.GemPurchasePersistenceEntity;

import java.util.List;

@Repository
public interface GemPurchasePersistenceRepository extends JpaRepository<GemPurchasePersistenceEntity, Long> {
    List<GemPurchasePersistenceEntity> findByUserId(Long userId);
}
