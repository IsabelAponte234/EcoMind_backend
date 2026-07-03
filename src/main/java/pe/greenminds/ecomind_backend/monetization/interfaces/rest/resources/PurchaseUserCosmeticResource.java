package pe.greenminds.ecomind_backend.monetization.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(
        name = "PurchaseUserCosmeticRequest",
        description = "Request payload for buying a cosmetic with gems. The price is looked up server-side and the user's gem balance is validated and charged atomically.",
        example = """
        {
          "userId": 1,
          "cosmeticId": 3
        }
        """
)
public record PurchaseUserCosmeticResource(
    @NotNull
    @Schema(description = "User ID buying the cosmetic", example = "1")
    Long userId,

    @NotNull
    @Schema(description = "Cosmetic ID to buy", example = "3")
    Long cosmeticId
) {
}
