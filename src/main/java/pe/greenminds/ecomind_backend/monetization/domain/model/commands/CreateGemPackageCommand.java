package pe.greenminds.ecomind_backend.monetization.domain.model.commands;

import java.math.BigDecimal;

public record CreateGemPackageCommand(
        String name,
        Integer gemAmount,
        BigDecimal realPrice,
        String currency
) {
}
