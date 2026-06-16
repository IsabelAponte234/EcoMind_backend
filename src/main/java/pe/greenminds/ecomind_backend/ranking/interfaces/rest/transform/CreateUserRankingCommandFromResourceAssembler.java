package pe.greenminds.ecomind_backend.ranking.interfaces.rest.transform;

import pe.greenminds.ecomind_backend.ranking.domain.model.commands.CreateUserRankingCommand;
import pe.greenminds.ecomind_backend.ranking.interfaces.rest.resources.CreateUserRankingResource;

public class CreateUserRankingCommandFromResourceAssembler {
    public static CreateUserRankingCommand toCommandFromResource(CreateUserRankingResource resource) {
        return new CreateUserRankingCommand(
                resource.userId(),
                resource.rankingId(),
                resource.rankPosition(),
                resource.score()
        );
    }
}
