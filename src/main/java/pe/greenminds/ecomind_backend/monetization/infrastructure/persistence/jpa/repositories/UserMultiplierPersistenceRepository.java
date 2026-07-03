package pe.greenminds.ecomind_backend.monetization.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.greenminds.ecomind_backend.monetization.infrastructure.persistence.jpa.entities.UserMultiplierPersistenceEntity;

import java.util.List;

@Repository
public interface UserMultiplierPersistenceRepository extends JpaRepository<UserMultiplierPersistenceEntity, Long> {
    List<UserMultiplierPersistenceEntity> findByUserId(Long userId);
}
