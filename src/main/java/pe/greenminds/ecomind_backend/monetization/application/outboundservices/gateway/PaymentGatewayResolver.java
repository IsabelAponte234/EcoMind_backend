package pe.greenminds.ecomind_backend.monetization.application.outboundservices.gateway;

import org.springframework.stereotype.Service;
import pe.greenminds.ecomind_backend.monetization.domain.model.valueobjects.PaymentMethod;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentGatewayResolver {

    private final List<PaymentGateway> gateways;

    public PaymentGatewayResolver(List<PaymentGateway> gateways) {
        this.gateways = gateways;
    }

    public Optional<PaymentGateway> resolve(PaymentMethod method) {
        return gateways.stream()
                .filter(gateway -> gateway.supports(method))
                .findFirst();
    }
}
