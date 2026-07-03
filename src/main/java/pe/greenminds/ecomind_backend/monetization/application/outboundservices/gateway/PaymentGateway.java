package pe.greenminds.ecomind_backend.monetization.application.outboundservices.gateway;

import pe.greenminds.ecomind_backend.monetization.domain.model.valueobjects.PaymentMethod;

/**
 * Port for charging money through an external payment gateway (Culqi, PayPal, etc.).
 * The application layer depends on this abstraction, not on any concrete provider,
 * so a different gateway can be plugged in by providing another implementation.
 */
public interface PaymentGateway {

    /** Whether this gateway can process the given payment method. */
    boolean supports(PaymentMethod method);

    ChargeResult charge(ChargeRequest request);
}
