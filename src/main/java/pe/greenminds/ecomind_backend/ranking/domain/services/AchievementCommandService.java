package pe.greenminds.ecomind_backend.ranking.domain.services;

import pe.greenminds.ecomind_backend.ranking.domain.model.commands.CreateAchievementCommand;

public interface AchievementCommandService {
    Long handle(CreateAchievementCommand command);
}
