package pe.greenminds.ecomind_backend.monetization.infrastructure.gateway.culqi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

import java.util.Map;

@Service
public class CulqiTokenizationService {

    private final RestClient restClient;
    private final String publicKey;

    public CulqiTokenizationService(@Value("${culqi.api.public-key}") String publicKey) {
        this.publicKey = publicKey;
        this.restClient = RestClient.builder().baseUrl("https://secure.culqi.com/v2").build();
    }

    public TokenizationResult tokenize(Map<String, Object> payload) {
        try {
            @SuppressWarnings("unchecked")
            Map<String, Object> response = restClient.post()
                    .uri("/tokens")
                    .header("Authorization", "Bearer " + publicKey)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(payload)
                    .retrieve()
                    .body(Map.class);

            var id = response != null ? String.valueOf(response.get("id")) : null;
            return TokenizationResult.success(id);
        } catch (RestClientResponseException e) {
            return TokenizationResult.failure(e.getResponseBodyAsString());
        } catch (Exception e) {
            return TokenizationResult.failure("Could not reach Culqi: " + e.getMessage());
        }
    }
}
