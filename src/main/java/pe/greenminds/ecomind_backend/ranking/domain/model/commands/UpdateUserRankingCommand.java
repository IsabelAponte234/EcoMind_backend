package pe.greenminds.ecomind_backend.ranking.domain.model.commands;

public record UpdateUserRankingCommand(Long userRankingId, Long userId, Long rankingId, int rankPosition, int score) {}
