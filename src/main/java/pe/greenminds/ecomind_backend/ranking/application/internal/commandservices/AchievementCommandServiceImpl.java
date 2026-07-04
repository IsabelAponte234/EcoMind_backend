package pe.greenminds.ecomind_backend.ranking.application.internal.commandservices;

import org.springframework.stereotype.Service;
import pe.greenminds.ecomind_backend.ranking.domain.model.commands.CreateAchievementCommand;
import pe.greenminds.ecomind_backend.ranking.domain.model.valueobjects.AchievementType;
import pe.greenminds.ecomind_backend.ranking.domain.services.AchievementCommandService;
import pe.greenminds.ecomind_backend.ranking.infrastructure.persistence.jpa.entities.AchievementEntity;
import pe.greenminds.ecomind_backend.ranking.infrastructure.persistence.jpa.repositories.AchievementRepository;

@Service
public class AchievementCommandServiceImpl implements AchievementCommandService {

    private final AchievementRepository achievementRepository;

    public AchievementCommandServiceImpl(AchievementRepository achievementRepository) {
        this.achievementRepository = achievementRepository;
    }

    @Override
    public Long handle(CreateAchievementCommand command) {
        var type = AchievementType.valueOf(command.type().toUpperCase());
        var achievement = new AchievementEntity(
                command.name(),
                command.description(),
                type,
                command.thresholdValue()
        );
        return achievementRepository.save(achievement).getId();
    }
}
