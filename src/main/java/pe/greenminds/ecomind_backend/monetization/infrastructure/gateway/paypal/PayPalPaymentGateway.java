package pe.greenminds.ecomind_backend.monetization.infrastructure.gateway.paypal;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;
import pe.greenminds.ecomind_backend.monetization.application.outboundservices.gateway.ChargeRequest;
import pe.greenminds.ecomind_backend.monetization.application.outboundservices.gateway.ChargeResult;
import pe.greenminds.ecomind_backend.monetization.application.outboundservices.gateway.PaymentGateway;
import pe.greenminds.ecomind_backend.monetization.domain.model.valueobjects.PaymentMethod;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

@Service
public class PayPalPaymentGateway implements PaymentGateway {

    private final RestClient restClient;
    private final String clientId;
    private final String secret;

    public PayPalPaymentGateway(
            @Value("${paypal.api.base-url}") String baseUrl,
            @Value("${paypal.api.client-id}") String clientId,
            @Value("${paypal.api.secret}") String secret
    ) {
        this.clientId = clientId;
        this.secret = secret;
        this.restClient = RestClient.builder().baseUrl(baseUrl).build();
    }

    @Override
    public boolean supports(PaymentMethod method) {
        return method == PaymentMethod.PAYPAL;
    }

    @Override
    public ChargeResult charge(ChargeRequest request) {
        try {
            var accessToken = fetchAccessToken();

            @SuppressWarnings("unchecked")
            Map<String, Object> response = restClient.post()
                    .uri("/v2/checkout/orders/{orderId}/capture", request.sourceToken())
                    .header("Authorization", "Bearer " + accessToken)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("{}")
                    .retrieve()
                    .body(Map.class);

            var status = response != null ? String.valueOf(response.get("status")) : null;
            if ("COMPLETED".equals(status)) {
                return ChargeResult.approved(String.valueOf(response.get("id")));
            }
            return ChargeResult.declined("PayPal order not completed (status: " + status + ")");
        } catch (RestClientResponseException e) {
            return ChargeResult.declined("PayPal declined the capture: " + e.getResponseBodyAsString());
        } catch (Exception e) {
            return ChargeResult.declined("Could not reach PayPal: " + e.getMessage());
        }
    }

    private String fetchAccessToken() {
        var basicAuth = Base64.getEncoder()
                .encodeToString((clientId + ":" + secret).getBytes(StandardCharsets.UTF_8));

        @SuppressWarnings("unchecked")
        Map<String, Object> response = restClient.post()
                .uri("/v1/oauth2/token")
                .header("Authorization", "Basic " + basicAuth)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body("grant_type=client_credentials")
                .retrieve()
                .body(Map.class);

        return response != null ? String.valueOf(response.get("access_token")) : null;
    }
}
