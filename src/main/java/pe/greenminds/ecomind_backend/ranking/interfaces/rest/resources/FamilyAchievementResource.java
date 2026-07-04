package pe.greenminds.ecomind_backend.ranking.interfaces.rest.resources;

public record FamilyAchievementResource(
        Long id,
        Long familyId,
        Long achievementId,
        String achievementName,
        String achievementDescription,
        String earnedAt
) {}
