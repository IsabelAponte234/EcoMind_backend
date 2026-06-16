package pe.greenminds.ecomind_backend.profile.domain.repositories;

import pe.greenminds.ecomind_backend.profile.domain.model.aggregates.Family;

import java.util.List;
import java.util.Optional;

public interface FamilyRepository {
    List<Family> findAll();
    Optional<Family> findById(Long id);
    Family save(Family family);
    void deleteById(Long id);
    boolean existsById(Long id);
}
