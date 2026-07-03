package pe.greenminds.ecomind_backend.monetization.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(
        name = "GemPackageResponse",
        description = "Gem package information response",
        example = """
        {
          "id": 1,
          "name": "Eco Pack",
          "gemAmount": 1000,
          "realPrice": 10.99,
          "currency": "PEN"
        }
        """
)
public record GemPackageResource(
    @Schema(description = "Gem package unique identifier", example = "1")
    Long id,

    @Schema(description = "Gem package name", example = "Eco Pack")
    String name,

    @Schema(description = "Amount of gems granted", example = "1000")
    Integer gemAmount,

    @Schema(description = "Real price to pay", example = "10.99")
    BigDecimal realPrice,

    @Schema(description = "Currency of the real price", example = "PEN")
    String currency
) {
}
