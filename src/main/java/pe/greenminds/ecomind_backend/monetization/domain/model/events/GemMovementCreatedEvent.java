package pe.greenminds.ecomind_backend.monetization.domain.model.events;

import pe.greenminds.ecomind_backend.monetization.domain.model.aggregates.GemMovement;
import pe.greenminds.ecomind_backend.monetization.domain.model.valueobjects.MovementOrigin;
import pe.greenminds.ecomind_backend.monetization.domain.model.valueobjects.MovementType;

public record GemMovementCreatedEvent(
        Long gemMovementId,
        Long userId,
        MovementType type,
        Integer amount,
        MovementOrigin origin,
        Long originId
) {
    public static GemMovementCreatedEvent from(GemMovement gemMovement) {
        return new GemMovementCreatedEvent(
                gemMovement.getId(),
                gemMovement.getUserId(),
                gemMovement.getType(),
                gemMovement.getAmount(),
                gemMovement.getOrigin(),
                gemMovement.getOriginId()
        );
    }
}
