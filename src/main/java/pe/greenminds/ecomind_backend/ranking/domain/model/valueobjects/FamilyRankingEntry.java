package pe.greenminds.ecomind_backend.ranking.domain.model.valueobjects;

public record FamilyRankingEntry(Long familyId, String familyName, int totalEcopoints, int position) {}
