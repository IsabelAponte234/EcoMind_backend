package pe.greenminds.ecomind_backend.monetization.infrastructure.persistence.jpa.assemblers;

import pe.greenminds.ecomind_backend.monetization.domain.model.aggregates.GemPackage;
import pe.greenminds.ecomind_backend.monetization.infrastructure.persistence.jpa.entities.GemPackagePersistenceEntity;

public class GemPackagePersistenceAssembler {

    private GemPackagePersistenceAssembler() {}

    public static GemPackage toDomainFromPersistence(GemPackagePersistenceEntity entity) {
        var gemPackage = new GemPackage(
                entity.getName(),
                entity.getGemAmount(),
                entity.getRealPrice(),
                entity.getCurrency()
        );
        gemPackage.setId(entity.getId());
        return gemPackage;
    }

    public static GemPackagePersistenceEntity toPersistenceFromDomain(GemPackage gemPackage) {
        var entity = new GemPackagePersistenceEntity();
        entity.setId(gemPackage.getId());
        entity.setName(gemPackage.getName());
        entity.setGemAmount(gemPackage.getGemAmount());
        entity.setRealPrice(gemPackage.getRealPrice());
        entity.setCurrency(gemPackage.getCurrency());
        return entity;
    }
}
