package pe.greenminds.ecomind_backend.monetization.domain.repositories;

import pe.greenminds.ecomind_backend.monetization.domain.model.aggregates.GemMovement;

import java.util.List;
import java.util.Optional;

public interface GemMovementRepository {
    Optional<GemMovement> findById(Long id);
    List<GemMovement> findAll();
    GemMovement save(GemMovement gemMovement);
    void deleteById(Long id);
    boolean existsById(Long id);
}
