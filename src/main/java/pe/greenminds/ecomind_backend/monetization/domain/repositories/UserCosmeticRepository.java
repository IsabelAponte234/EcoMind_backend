package pe.greenminds.ecomind_backend.monetization.domain.repositories;

import pe.greenminds.ecomind_backend.monetization.domain.model.aggregates.UserCosmetic;

import java.util.List;
import java.util.Optional;

public interface UserCosmeticRepository {
    Optional<UserCosmetic> findById(Long id);
    List<UserCosmetic> findAll();
    List<UserCosmetic> findByUserId(Long userId);
    UserCosmetic save(UserCosmetic userCosmetic);
    void deleteById(Long id);
    boolean existsById(Long id);
    boolean existsByUserIdAndCosmeticId(Long userId, Long cosmeticId);
}
