package pe.greenminds.ecomind_backend.monetization.domain.model.events;

import pe.greenminds.ecomind_backend.monetization.domain.model.aggregates.GemPackage;

import java.math.BigDecimal;

public record GemPackageCreatedEvent(
        Long gemPackageId,
        String name,
        Integer gemAmount,
        BigDecimal realPrice,
        String currency
) {
    public static GemPackageCreatedEvent from(GemPackage gemPackage) {
        return new GemPackageCreatedEvent(
                gemPackage.getId(),
                gemPackage.getName(),
                gemPackage.getGemAmount(),
                gemPackage.getRealPrice(),
                gemPackage.getCurrency()
        );
    }
}
