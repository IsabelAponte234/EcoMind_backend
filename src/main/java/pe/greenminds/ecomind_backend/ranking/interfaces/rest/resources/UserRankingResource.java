package pe.greenminds.ecomind_backend.ranking.interfaces.rest.resources;

public record UserRankingResource(Long id, Long userId, Long rankingId, int rankPosition, int score) {}
