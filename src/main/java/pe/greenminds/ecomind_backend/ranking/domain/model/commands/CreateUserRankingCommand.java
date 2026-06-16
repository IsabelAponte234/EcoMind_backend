package pe.greenminds.ecomind_backend.ranking.domain.model.commands;

public record CreateUserRankingCommand(Long userId, Long rankingId, int rankPosition, int score) {}
