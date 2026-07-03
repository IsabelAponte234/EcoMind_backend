package pe.greenminds.ecomind_backend.monetization.application.outboundservices.gateway;

import pe.greenminds.ecomind_backend.monetization.domain.model.valueobjects.PaymentMethod;

public interface PaymentGateway {

    boolean supports(PaymentMethod method);

    ChargeResult charge(ChargeRequest request);
}
