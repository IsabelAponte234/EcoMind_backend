package pe.greenminds.ecomind_backend.monetization.domain.model.commands;

import pe.greenminds.ecomind_backend.monetization.domain.model.valueobjects.PaymentMethod;

public record CreateGemPurchaseCheckoutCommand(
        Long userId,
        Long packageId,
        PaymentMethod paymentMethod
) {
}
