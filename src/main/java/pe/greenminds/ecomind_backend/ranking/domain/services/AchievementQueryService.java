package pe.greenminds.ecomind_backend.ranking.domain.services;

import pe.greenminds.ecomind_backend.ranking.domain.model.queries.GetAchievementByIdQuery;
import pe.greenminds.ecomind_backend.ranking.domain.model.queries.GetAllAchievementsQuery;
import pe.greenminds.ecomind_backend.ranking.infrastructure.persistence.jpa.entities.AchievementEntity;

import java.util.List;
import java.util.Optional;

public interface AchievementQueryService {
    List<AchievementEntity> handle(GetAllAchievementsQuery query);
    Optional<AchievementEntity> handle(GetAchievementByIdQuery query);
}
