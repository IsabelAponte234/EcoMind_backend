package pe.greenminds.ecomind_backend.monetization.application.outboundservices.gateway;

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
