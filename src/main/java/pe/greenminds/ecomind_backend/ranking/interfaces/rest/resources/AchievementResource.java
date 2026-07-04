package pe.greenminds.ecomind_backend.ranking.interfaces.rest.resources;

public record AchievementResource(
        Long id,
        String name,
        String description,
        String type,
        Integer thresholdValue
) {}
