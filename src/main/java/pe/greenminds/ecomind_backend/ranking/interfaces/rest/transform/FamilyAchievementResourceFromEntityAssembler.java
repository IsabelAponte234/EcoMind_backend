package pe.greenminds.ecomind_backend.ranking.interfaces.rest.transform;

import pe.greenminds.ecomind_backend.ranking.infrastructure.persistence.jpa.entities.AchievementEntity;
import pe.greenminds.ecomind_backend.ranking.infrastructure.persistence.jpa.entities.FamilyAchievementEntity;
import pe.greenminds.ecomind_backend.ranking.interfaces.rest.resources.FamilyAchievementResource;

import java.text.SimpleDateFormat;

public class FamilyAchievementResourceFromEntityAssembler {
    public static FamilyAchievementResource toResourceFromEntity(
            FamilyAchievementEntity entity,
            AchievementEntity achievement
    ) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        return new FamilyAchievementResource(
                entity.getId(),
                entity.getFamilyId(),
                entity.getAchievementId(),
                achievement != null ? achievement.getName() : null,
                achievement != null ? achievement.getDescription() : null,
                sdf.format(entity.getEarnedAt())
        );
    }
}
