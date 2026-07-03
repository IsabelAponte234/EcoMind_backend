package pe.greenminds.ecomind_backend.monetization.application.outboundservices.gateway;

/**
 * A request to charge money through a payment gateway.
 *
 * @param amountInCents amount to charge expressed in the smallest currency unit (e.g. céntimos for PEN)
 * @param currencyCode  ISO currency code, e.g. "PEN"
 * @param email         payer email
 * @param sourceToken   one-time token that represents the payment source (card/Yape), created by the gateway on the client side
 * @param description   human readable description of what is being paid
 */
public record ChargeRequest(
        Integer amountInCents,
        String currencyCode,
        String email,
        String sourceToken,
        String description
) {
}
