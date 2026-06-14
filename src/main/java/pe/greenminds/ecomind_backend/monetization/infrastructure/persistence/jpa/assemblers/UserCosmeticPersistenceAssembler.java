package pe.greenminds.ecomind_backend.monetization.infrastructure.persistence.jpa.assemblers;

import pe.greenminds.ecomind_backend.monetization.domain.model.aggregates.UserCosmetic;
import pe.greenminds.ecomind_backend.monetization.infrastructure.persistence.jpa.entities.UserCosmeticPersistenceEntity;

public class UserCosmeticPersistenceAssembler {

    private UserCosmeticPersistenceAssembler() {}

    public static UserCosmetic toDomainFromPersistence(UserCosmeticPersistenceEntity entity) {
        var userCosmetic = new UserCosmetic(
                entity.getUserId(),
                entity.getCosmeticId(),
                entity.getAcquiredAt(),
                entity.getEquipped()
        );
        userCosmetic.setId(entity.getId());
        return userCosmetic;
    }

    public static UserCosmeticPersistenceEntity toPersistenceFromDomain(UserCosmetic userCosmetic) {
        var entity = new UserCosmeticPersistenceEntity();
        entity.setId(userCosmetic.getId());
        entity.setUserId(userCosmetic.getUserId());
        entity.setCosmeticId(userCosmetic.getCosmeticId());
        entity.setAcquiredAt(userCosmetic.getAcquiredAt());
        entity.setEquipped(userCosmetic.getEquipped());
        return entity;
    }
}
