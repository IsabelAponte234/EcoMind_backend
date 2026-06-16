package pe.greenminds.ecomind_backend.ranking.domain.model.commands;

public record UpdateRankingCommand(Long rankingId, String name, String type, boolean status) {}
