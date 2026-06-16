package pe.greenminds.ecomind_backend.profile.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.greenminds.ecomind_backend.profile.infrastructure.persistence.jpa.entities.FamilyPersistenceEntity;

@Repository
public interface FamilyPersistenceRepository extends JpaRepository<FamilyPersistenceEntity, Long> {
}
