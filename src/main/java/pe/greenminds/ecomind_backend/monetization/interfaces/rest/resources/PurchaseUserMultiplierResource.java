package pe.greenminds.ecomind_backend.monetization.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(
        name = "PurchaseUserMultiplierRequest",
        description = "Request payload for buying a multiplier with gems. The cost and duration are looked up server-side and the user's gem balance is validated and charged atomically.",
        example = """
        {
          "userId": 1,
          "multiplierId": 2
        }
        """
)
public record PurchaseUserMultiplierResource(
    @NotNull
    @Schema(description = "User ID buying the multiplier", example = "1")
    Long userId,

    @NotNull
    @Schema(description = "Multiplier ID to buy", example = "2")
    Long multiplierId
) {
}
