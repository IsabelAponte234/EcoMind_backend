package pe.greenminds.ecomind_backend.monetization.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

@Schema(
        name = "CreateGemPackageRequest",
        description = "Request payload for creating a new gem package",
        example = """
        {
          "name": "Eco Pack",
          "gemAmount": 1000,
          "realPrice": 10.99,
          "currency": "PEN"
        }
        """
)
public record CreateGemPackageResource(
    @NotBlank
    @Schema(description = "Gem package name", example = "Eco Pack")
    String name,

    @NotNull
    @Positive
    @Schema(description = "Amount of gems granted", example = "1000")
    Integer gemAmount,

    @NotNull
    @Positive
    @Schema(description = "Real price to pay", example = "10.99")
    BigDecimal realPrice,

    @NotBlank
    @Schema(description = "Currency of the real price", example = "PEN")
    String currency
) {
}
