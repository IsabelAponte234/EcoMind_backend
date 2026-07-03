package pe.greenminds.ecomind_backend.monetization.domain.model.commands;

import java.time.LocalDateTime;

public record CreateUserMultiplierCommand(
        Long userId,
        Long multiplierId,
        LocalDateTime startDate,
        LocalDateTime endDate
) {
}
