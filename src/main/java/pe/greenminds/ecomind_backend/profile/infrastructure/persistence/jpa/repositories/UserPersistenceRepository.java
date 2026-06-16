package pe.greenminds.ecomind_backend.profile.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.greenminds.ecomind_backend.profile.infrastructure.persistence.jpa.entities.UserPersistenceEntity;

@Repository
public interface UserPersistenceRepository extends JpaRepository<UserPersistenceEntity, Long> {
    boolean existsByEmail(String email);
}
