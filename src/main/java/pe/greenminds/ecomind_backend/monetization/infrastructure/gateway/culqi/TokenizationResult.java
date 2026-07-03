package pe.greenminds.ecomind_backend.monetization.infrastructure.gateway.culqi;

public record TokenizationResult(
        boolean success,
        String tokenId,
        String errorMessage
) {
    public static TokenizationResult success(String tokenId) {
        return new TokenizationResult(true, tokenId, null);
    }

    public static TokenizationResult failure(String errorMessage) {
        return new TokenizationResult(false, null, errorMessage);
    }
}
