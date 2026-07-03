package pe.greenminds.ecomind_backend.monetization.domain.repositories;

import pe.greenminds.ecomind_backend.monetization.domain.model.aggregates.UserMultiplier;

import java.util.List;
import java.util.Optional;

public interface UserMultiplierRepository {
    Optional<UserMultiplier> findById(Long id);
    List<UserMultiplier> findAll();
    List<UserMultiplier> findByUserId(Long userId);
    UserMultiplier save(UserMultiplier userMultiplier);
    void deleteById(Long id);
    boolean existsById(Long id);
}
