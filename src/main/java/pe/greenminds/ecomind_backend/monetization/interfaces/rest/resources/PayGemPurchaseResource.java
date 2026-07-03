package pe.greenminds.ecomind_backend.monetization.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(
        name = "PayGemPurchaseRequest",
        description = "Request payload to pay a PENDING gem purchase. The card/Yape token is created on the client with Culqi.js; only the one-time token reaches the server.",
        example = """
        {
          "sourceToken": "tkn_test_xxxxxxxxxxxx",
          "email": "isabel@ecomind.com"
        }
        """
)
public record PayGemPurchaseResource(
    @NotBlank(message = "is required")
    @Schema(description = "One-time payment token created by Culqi.js on the client", example = "tkn_test_xxxxxxxxxxxx")
    String sourceToken,

    @NotBlank(message = "is required")
    @Email
    @Schema(description = "Payer email", example = "isabel@ecomind.com")
    String email
) {
}
