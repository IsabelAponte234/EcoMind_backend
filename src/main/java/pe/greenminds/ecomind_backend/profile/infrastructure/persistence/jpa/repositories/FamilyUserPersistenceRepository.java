package pe.greenminds.ecomind_backend.profile.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.greenminds.ecomind_backend.profile.infrastructure.persistence.jpa.entities.FamilyUserPersistenceEntity;

import java.util.List;

@Repository
public interface FamilyUserPersistenceRepository extends JpaRepository<FamilyUserPersistenceEntity, Long> {
    List<FamilyUserPersistenceEntity> findByUserId(Long userId);
    List<FamilyUserPersistenceEntity> findByFamilyId(Long familyId);
    boolean existsByUserIdAndFamilyId(Long userId, Long familyId);
    boolean existsByUserId(Long userId);
    void deleteByUserId(Long userId);
    void deleteByFamilyId(Long familyId);
}
