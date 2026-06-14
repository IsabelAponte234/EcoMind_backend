package pe.greenminds.ecomind_backend.monetization.domain.model.commands;

import java.time.LocalDateTime;

public record UpdateUserCosmeticCommand(
        Long userCosmeticId,
        Long userId,
        Long cosmeticId,
        LocalDateTime acquiredAt,
        Boolean equipped
) {
}
