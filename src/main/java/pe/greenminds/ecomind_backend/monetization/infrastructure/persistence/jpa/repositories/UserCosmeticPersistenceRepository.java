package pe.greenminds.ecomind_backend.monetization.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.greenminds.ecomind_backend.monetization.infrastructure.persistence.jpa.entities.UserCosmeticPersistenceEntity;

import java.util.List;

@Repository
public interface UserCosmeticPersistenceRepository extends JpaRepository<UserCosmeticPersistenceEntity, Long> {
    List<UserCosmeticPersistenceEntity> findByUserId(Long userId);
    boolean existsByUserIdAndCosmeticId(Long userId, Long cosmeticId);
}
