package pe.greenminds.ecomind_backend.monetization.infrastructure.persistence.jpa.assemblers;

import pe.greenminds.ecomind_backend.monetization.domain.model.aggregates.UserMultiplier;
import pe.greenminds.ecomind_backend.monetization.infrastructure.persistence.jpa.entities.UserMultiplierPersistenceEntity;

public class UserMultiplierPersistenceAssembler {

    private UserMultiplierPersistenceAssembler() {}

    public static UserMultiplier toDomainFromPersistence(UserMultiplierPersistenceEntity entity) {
        var userMultiplier = new UserMultiplier(
                entity.getUserId(),
                entity.getMultiplierId(),
                entity.getStartDate(),
                entity.getEndDate()
        );
        userMultiplier.setId(entity.getId());
        return userMultiplier;
    }

    public static UserMultiplierPersistenceEntity toPersistenceFromDomain(UserMultiplier userMultiplier) {
        var entity = new UserMultiplierPersistenceEntity();
        entity.setId(userMultiplier.getId());
        entity.setUserId(userMultiplier.getUserId());
        entity.setMultiplierId(userMultiplier.getMultiplierId());
        entity.setStartDate(userMultiplier.getStartDate());
        entity.setEndDate(userMultiplier.getEndDate());
        return entity;
    }
}
