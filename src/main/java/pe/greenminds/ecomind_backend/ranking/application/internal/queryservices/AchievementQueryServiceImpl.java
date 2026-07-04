package pe.greenminds.ecomind_backend.ranking.application.internal.queryservices;

import org.springframework.stereotype.Service;
import pe.greenminds.ecomind_backend.ranking.domain.model.queries.GetAchievementByIdQuery;
import pe.greenminds.ecomind_backend.ranking.domain.model.queries.GetAllAchievementsQuery;
import pe.greenminds.ecomind_backend.ranking.domain.services.AchievementQueryService;
import pe.greenminds.ecomind_backend.ranking.infrastructure.persistence.jpa.entities.AchievementEntity;
import pe.greenminds.ecomind_backend.ranking.infrastructure.persistence.jpa.repositories.AchievementRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AchievementQueryServiceImpl implements AchievementQueryService {

    private final AchievementRepository achievementRepository;

    public AchievementQueryServiceImpl(AchievementRepository achievementRepository) {
        this.achievementRepository = achievementRepository;
    }

    @Override
    public List<AchievementEntity> handle(GetAllAchievementsQuery query) {
        return achievementRepository.findAll();
    }

    @Override
    public Optional<AchievementEntity> handle(GetAchievementByIdQuery query) {
        return achievementRepository.findById(query.achievementId());
    }
}
