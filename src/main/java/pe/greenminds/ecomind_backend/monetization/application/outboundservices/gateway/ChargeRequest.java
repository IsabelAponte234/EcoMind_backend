package pe.greenminds.ecomind_backend.monetization.application.outboundservices.gateway;

public record ChargeRequest(
        Integer amountInCents,
        String currencyCode,
        String email,
        String sourceToken,
        String description
) {
}
