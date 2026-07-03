package pe.greenminds.ecomind_backend.monetization.domain.model.commands;

public record ConfirmGemPurchaseByChargeCommand(
        String chargeReference,
        boolean approved
) {
}
