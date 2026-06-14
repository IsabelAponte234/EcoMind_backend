package pe.greenminds.ecomind_backend.monetization.domain.model.commands;

import java.time.LocalDateTime;

public record CreateUserCosmeticCommand(
        Long userId,
        Long cosmeticId,
        LocalDateTime acquiredAt,
        Boolean equipped
) {
}
