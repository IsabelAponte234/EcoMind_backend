package pe.greenminds.ecomind_backend.monetization.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.greenminds.ecomind_backend.monetization.application.commandservices.GemPurchaseCommandService;
import pe.greenminds.ecomind_backend.monetization.domain.model.commands.ConfirmGemPurchaseByChargeCommand;

import java.util.Map;

/**
 * Receives asynchronous payment notifications (webhooks) from the payment gateways.
 *
 * <p>Webhooks are server-to-server: the gateway (not the browser) tells us the final
 * outcome of a charge. This confirms the matching gem purchase idempotently — if the
 * synchronous {@code /pay} already settled it, the webhook is a harmless no-op.</p>
 */
@RestController
@RequestMapping(value = "/api/v1/webhooks", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Payment Webhooks", description = "Server-to-server payment confirmations")
public class PaymentWebhookController {

    private final GemPurchaseCommandService gemPurchaseCommandService;

    public PaymentWebhookController(GemPurchaseCommandService gemPurchaseCommandService) {
        this.gemPurchaseCommandService = gemPurchaseCommandService;
    }

    @PostMapping("/culqi")
    @Operation(
            summary = "Culqi payment webhook",
            description = "Endpoint Culqi calls when a charge changes state. Configure its URL in the Culqi dashboard. Always answers 200 so Culqi does not retry."
    )
    public ResponseEntity<Map<String, String>> handleCulqiWebhook(@RequestBody Map<String, Object> payload) {
        var chargeId = extractChargeId(payload);
        if (chargeId != null) {
            var approved = isSuccess(payload);
            gemPurchaseCommandService.handle(new ConfirmGemPurchaseByChargeCommand(chargeId, approved));
        }
        // Acknowledge regardless: a non-200 makes the gateway keep retrying.
        return ResponseEntity.ok(Map.of("received", "true"));
    }

    @SuppressWarnings("unchecked")
    private String extractChargeId(Map<String, Object> payload) {
        var data = payload.get("data");
        if (data instanceof Map<?, ?> dataMap) {
            var id = ((Map<String, Object>) dataMap).get("id");
            return id != null ? id.toString() : null;
        }
        var id = payload.get("id");
        return id != null ? id.toString() : null;
    }

    private boolean isSuccess(Map<String, Object> payload) {
        var type = payload.get("type");
        if (type == null) {
            return true;
        }
        var normalized = type.toString().toLowerCase();
        return !(normalized.contains("fail") || normalized.contains("denied") || normalized.contains("declin"));
    }
}
