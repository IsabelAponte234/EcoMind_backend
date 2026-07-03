package pe.greenminds.ecomind_backend.monetization.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.greenminds.ecomind_backend.monetization.infrastructure.gateway.culqi.CulqiTokenizationService;
import pe.greenminds.ecomind_backend.shared.application.result.ApplicationError;
import pe.greenminds.ecomind_backend.shared.interfaces.rest.transform.ErrorResponseAssembler;

import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1/gem_purchase/tokenize", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Culqi Tokenization", description = "Server-side proxy to Culqi's public tokenization API, avoiding browser CORS restrictions")
public class CulqiTokenizationController {

    private final CulqiTokenizationService tokenizationService;

    public CulqiTokenizationController(CulqiTokenizationService tokenizationService) {
        this.tokenizationService = tokenizationService;
    }

    @PostMapping
    @Operation(
            summary = "Tokenize a card or Yape payment source with Culqi",
            description = "Forwards the raw card/Yape data to Culqi's tokenization API server-side, using the public key. The browser never calls Culqi directly."
    )
    public ResponseEntity<?> tokenize(@RequestBody Map<String, Object> payload) {
        var result = tokenizationService.tokenize(payload);
        if (result.success()) {
            return ResponseEntity.ok(Map.of("id", result.tokenId()));
        }
        var error = ApplicationError.businessRuleViolation("Tokenization failed", result.errorMessage());
        return ErrorResponseAssembler.toErrorResponseFromApplicationError(error);
    }
}
