package pe.greenminds.ecomind_backend.monetization.domain.repositories;

import pe.greenminds.ecomind_backend.monetization.domain.model.aggregates.GemPackage;

import java.util.List;
import java.util.Optional;

public interface GemPackageRepository {
    Optional<GemPackage> findById(Long id);
    List<GemPackage> findAll();
    GemPackage save(GemPackage gemPackage);
    void deleteById(Long id);
    boolean existsById(Long id);
}
