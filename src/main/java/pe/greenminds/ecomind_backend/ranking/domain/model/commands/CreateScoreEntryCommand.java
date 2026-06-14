package pe.greenminds.ecomind_backend.ranking.domain.model.commands;

public record CreateScoreEntryCommand(Long userId, int score, String entryType, String description) {}
