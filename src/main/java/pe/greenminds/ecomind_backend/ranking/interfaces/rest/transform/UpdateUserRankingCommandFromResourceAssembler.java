package pe.greenminds.ecomind_backend.ranking.interfaces.rest.transform;

import pe.greenminds.ecomind_backend.ranking.domain.model.commands.UpdateUserRankingCommand;
import pe.greenminds.ecomind_backend.ranking.interfaces.rest.resources.UpdateUserRankingResource;

public class UpdateUserRankingCommandFromResourceAssembler {
    public static UpdateUserRankingCommand toCommandFromResource(Long userRankingId, UpdateUserRankingResource resource) {
        return new UpdateUserRankingCommand(
                userRankingId,
                resource.userId(),
                resource.rankingId(),
                resource.rankPosition(),
                resource.score()
        );
    }
}
