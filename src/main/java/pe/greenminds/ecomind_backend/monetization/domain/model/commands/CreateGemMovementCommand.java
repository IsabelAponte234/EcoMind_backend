package pe.greenminds.ecomind_backend.monetization.domain.model.commands;

import pe.greenminds.ecomind_backend.monetization.domain.model.valueobjects.MovementOrigin;
import pe.greenminds.ecomind_backend.monetization.domain.model.valueobjects.MovementType;

public record CreateGemMovementCommand(
        Long userId,
        MovementType type,
        Integer amount,
        MovementOrigin origin,
        Long originId
) {
}
