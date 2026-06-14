package pe.greenminds.ecomind_backend.monetization.domain.repositories;

import pe.greenminds.ecomind_backend.monetization.domain.model.aggregates.GemPurchase;

import java.util.List;
import java.util.Optional;

public interface GemPurchaseRepository {
    Optional<GemPurchase> findById(Long id);
    List<GemPurchase> findAll();
    GemPurchase save(GemPurchase gemPurchase);
    void deleteById(Long id);
    boolean existsById(Long id);
}
