package pe.greenminds.ecomind_backend.ranking.interfaces.rest.resources;

public record ScoreEntryResource(Long id, Long userId, int score, String entryType, String description, String entryDate) {}
