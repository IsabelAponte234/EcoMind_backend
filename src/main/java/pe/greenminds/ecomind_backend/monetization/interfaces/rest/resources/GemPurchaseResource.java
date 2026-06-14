package pe.greenminds.ecomind_backend.monetization.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(
        name = "GemPurchaseResponse",
        description = "Gem purchase information response",
        example = """
        {
          "id": 1,
          "userId": 10,
          "packageId": 2,
          "purchaseDate": "2026-06-14T10:30:00",
          "amountPaid": 1.99,
          "paymentStatus": "COMPLETED",
          "paymentReference": "PAY-123456"
        }
        """
)
public record GemPurchaseResource(
    @Schema(description = "Gem purchase unique identifier", example = "1")
    Long id,

    @Schema(description = "User ID who made the purchase", example = "10")
    Long userId,

    @Schema(description = "Package ID purchased", example = "2")
    Long packageId,

    @Schema(description = "Date and time of purchase", example = "2026-06-14T10:30:00")
    LocalDateTime purchaseDate,

    @Schema(description = "Amount paid", example = "1.99")
    BigDecimal amountPaid,

    @Schema(description = "Payment status", example = "COMPLETED")
    String paymentStatus,

    @Schema(description = "Payment reference", example = "PAY-123456")
    String paymentReference
) {
}
