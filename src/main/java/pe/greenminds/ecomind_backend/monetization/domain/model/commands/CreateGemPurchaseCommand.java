package pe.greenminds.ecomind_backend.monetization.domain.model.commands;

import pe.greenminds.ecomind_backend.monetization.domain.model.valueobjects.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CreateGemPurchaseCommand(
        Long userId,
        Long packageId,
        LocalDateTime purchaseDate,
        BigDecimal amountPaid,
        PaymentStatus paymentStatus,
        String paymentReference
) {
}
