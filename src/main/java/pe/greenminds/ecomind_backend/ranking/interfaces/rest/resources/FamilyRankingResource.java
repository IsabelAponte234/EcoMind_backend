package pe.greenminds.ecomind_backend.ranking.interfaces.rest.resources;

public record FamilyRankingResource(Long familyId, String familyName, int totalEcopoints, int position) {}
