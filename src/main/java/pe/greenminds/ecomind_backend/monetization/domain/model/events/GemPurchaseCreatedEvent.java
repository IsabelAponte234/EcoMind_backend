package pe.greenminds.ecomind_backend.monetization.domain.model.events;

import pe.greenminds.ecomind_backend.monetization.domain.model.aggregates.GemPurchase;
import pe.greenminds.ecomind_backend.monetization.domain.model.valueobjects.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record GemPurchaseCreatedEvent(
        Long gemPurchaseId,
        Long userId,
        Long packageId,
        LocalDateTime purchaseDate,
        BigDecimal amountPaid,
        PaymentStatus paymentStatus,
        String paymentReference
) {
    public static GemPurchaseCreatedEvent from(GemPurchase gemPurchase) {
        return new GemPurchaseCreatedEvent(
                gemPurchase.getId(),
                gemPurchase.getUserId(),
                gemPurchase.getPackageId(),
                gemPurchase.getPurchaseDate(),
                gemPurchase.getAmountPaid(),
                gemPurchase.getPaymentStatus(),
                gemPurchase.getPaymentReference()
        );
    }
}
