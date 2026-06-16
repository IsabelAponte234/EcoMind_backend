package pe.greenminds.ecomind_backend.ranking.interfaces.rest.transform;

import pe.greenminds.ecomind_backend.ranking.domain.model.commands.CreateRankingCommand;
import pe.greenminds.ecomind_backend.ranking.interfaces.rest.resources.CreateRankingResource;

public class CreateRankingCommandFromResourceAssembler {
    public static CreateRankingCommand toCommandFromResource(CreateRankingResource resource) {
        return new CreateRankingCommand(resource.name(), resource.type());
    }
}
