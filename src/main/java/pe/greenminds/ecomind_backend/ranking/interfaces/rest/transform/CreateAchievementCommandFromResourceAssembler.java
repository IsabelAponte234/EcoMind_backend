package pe.greenminds.ecomind_backend.ranking.interfaces.rest.transform;

import pe.greenminds.ecomind_backend.ranking.domain.model.commands.CreateAchievementCommand;
import pe.greenminds.ecomind_backend.ranking.interfaces.rest.resources.CreateAchievementResource;

public class CreateAchievementCommandFromResourceAssembler {
    public static CreateAchievementCommand toCommandFromResource(CreateAchievementResource resource) {
        return new CreateAchievementCommand(
                resource.name(),
                resource.description(),
                resource.type(),
                resource.thresholdValue()
        );
    }
}
