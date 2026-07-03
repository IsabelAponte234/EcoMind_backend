package pe.greenminds.ecomind_backend.monetization.domain.model.commands;

public record PayGemPurchaseCommand(
        Long gemPurchaseId,
        String sourceToken,
        String email
) {
}
