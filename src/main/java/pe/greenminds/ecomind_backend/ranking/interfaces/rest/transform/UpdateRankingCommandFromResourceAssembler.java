package pe.greenminds.ecomind_backend.ranking.interfaces.rest.transform;

import pe.greenminds.ecomind_backend.ranking.domain.model.commands.UpdateRankingCommand;
import pe.greenminds.ecomind_backend.ranking.interfaces.rest.resources.UpdateRankingResource;

public class UpdateRankingCommandFromResourceAssembler {
    public static UpdateRankingCommand toCommandFromResource(Long rankingId, UpdateRankingResource resource) {
        return new UpdateRankingCommand(
                rankingId,
                resource.name(),
                resource.type(),
                resource.status()
        );
    }
}
