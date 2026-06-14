package pe.greenminds.ecomind_backend.monetization.infrastructure.persistence.jpa.assemblers;

import pe.greenminds.ecomind_backend.monetization.domain.model.aggregates.GemMovement;
import pe.greenminds.ecomind_backend.monetization.infrastructure.persistence.jpa.entities.GemMovementPersistenceEntity;

public class GemMovementPersistenceAssembler {

    private GemMovementPersistenceAssembler() {}

    public static GemMovement toDomainFromPersistence(GemMovementPersistenceEntity entity) {
        var gemMovement = new GemMovement(
                entity.getUserId(),
                entity.getType(),
                entity.getAmount(),
                entity.getOrigin(),
                entity.getOriginId()
        );
        gemMovement.setId(entity.getId());
        return gemMovement;
    }

    public static GemMovementPersistenceEntity toPersistenceFromDomain(GemMovement gemMovement) {
        var entity = new GemMovementPersistenceEntity();
        entity.setId(gemMovement.getId());
        entity.setUserId(gemMovement.getUserId());
        entity.setType(gemMovement.getType());
        entity.setAmount(gemMovement.getAmount());
        entity.setOrigin(gemMovement.getOrigin());
        entity.setOriginId(gemMovement.getOriginId());
        return entity;
    }
}
