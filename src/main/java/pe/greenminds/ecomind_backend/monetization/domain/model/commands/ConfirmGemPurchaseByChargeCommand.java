package pe.greenminds.ecomind_backend.monetization.domain.model.commands;

/**
 * Confirms a gem purchase from an asynchronous payment gateway notification (webhook),
 * located by the gateway charge reference stored on the purchase.
 *
 * @param chargeReference the gateway charge id previously stored as the purchase payment reference
 * @param approved        whether the gateway reported the charge as paid
 */
public record ConfirmGemPurchaseByChargeCommand(
        String chargeReference,
        boolean approved
) {
}
