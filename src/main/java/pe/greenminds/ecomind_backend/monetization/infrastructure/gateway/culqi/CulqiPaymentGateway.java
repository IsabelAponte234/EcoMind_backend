package pe.greenminds.ecomind_backend.monetization.infrastructure.gateway.culqi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;
import pe.greenminds.ecomind_backend.monetization.application.outboundservices.gateway.ChargeRequest;
import pe.greenminds.ecomind_backend.monetization.application.outboundservices.gateway.ChargeResult;
import pe.greenminds.ecomind_backend.monetization.application.outboundservices.gateway.PaymentGateway;
import pe.greenminds.ecomind_backend.monetization.domain.model.valueobjects.PaymentMethod;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class CulqiPaymentGateway implements PaymentGateway {

    private final RestClient restClient;
    private final String secretKey;

    public CulqiPaymentGateway(
            @Value("${culqi.api.base-url}") String baseUrl,
            @Value("${culqi.api.secret-key}") String secretKey
    ) {
        this.secretKey = secretKey;
        this.restClient = RestClient.builder().baseUrl(baseUrl).build();
    }

    @Override
    public boolean supports(PaymentMethod method) {
        return method == PaymentMethod.CARD || method == PaymentMethod.YAPE;
    }

    @Override
    public ChargeResult charge(ChargeRequest request) {
        var body = new LinkedHashMap<String, Object>();
        body.put("amount", request.amountInCents());
        body.put("currency_code", request.currencyCode());
        body.put("email", request.email());
        body.put("source_id", request.sourceToken());
        body.put("description", request.description());

        try {
            @SuppressWarnings("unchecked")
            Map<String, Object> response = restClient.post()
                    .uri("/charges")
                    .header("Authorization", "Bearer " + secretKey)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(body)
                    .retrieve()
                    .body(Map.class);

            var chargeId = response != null ? String.valueOf(response.get("id")) : null;
            return ChargeResult.approved(chargeId);
        } catch (RestClientResponseException e) {
            return ChargeResult.declined("Culqi declined the charge: " + e.getResponseBodyAsString());
        } catch (Exception e) {
            return ChargeResult.declined("Could not reach Culqi: " + e.getMessage());
        }
    }
}
