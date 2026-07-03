package pe.greenminds.ecomind_backend.monetization.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(
        name = "CreateGemPurchaseRequest",
        description = "Request payload for creating a new gem purchase",
        example = """
        {
          "userId": 10,
          "packageId": 2,
          "purchaseDate": "2026-06-14T10:30:00",
          "amountPaid": 1.99,
          "paymentStatus": "COMPLETED",
          "paymentReference": "PAY-123456"
        }
        """
)
public record CreateGemPurchaseResource(
    @NotNull
    @Schema(description = "User ID who made the purchase", example = "10")
    Long userId,

    @NotNull
    @Schema(description = "Package ID purchased", example = "2")
    Long packageId,

    @NotNull
    @Schema(description = "Date and time of purchase", example = "2026-06-14T10:30:00")
    LocalDateTime purchaseDate,

    @NotNull
    @PositiveOrZero
    @Schema(description = "Amount paid", example = "1.99")
    BigDecimal amountPaid,

    @NotBlank(message = "is required")
    @Schema(description = "Payment status", example = "COMPLETED")
    String paymentStatus,

    @Schema(description = "Payment reference", example = "PAY-123456")
    String paymentReference,

    @Pattern(regexp = "(?i)(card|yape|paypal)?", message = "must be CARD, YAPE or PAYPAL")
    @Schema(description = "Payment method used. Defaults to CARD when omitted.", example = "card")
    String paymentMethod
) {
}
