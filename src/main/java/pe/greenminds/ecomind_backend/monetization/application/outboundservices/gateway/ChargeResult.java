package pe.greenminds.ecomind_backend.monetization.application.outboundservices.gateway;

/**
 * The outcome of a charge attempt through a payment gateway.
 *
 * @param approved whether the charge was approved by the gateway
 * @param chargeId the gateway reference for the charge (null when it failed)
 * @param message  a human readable message (approval or decline reason)
 */
public record ChargeResult(
        boolean approved,
        String chargeId,
        String message
) {
    public static ChargeResult approved(String chargeId) {
        return new ChargeResult(true, chargeId, "Charge approved");
    }

    public static ChargeResult declined(String message) {
        return new ChargeResult(false, null, message);
    }
}
