package pe.greenminds.ecomind_backend.ranking.interfaces.rest.transform;

import pe.greenminds.ecomind_backend.ranking.domain.model.commands.CreateScoreEntryCommand;
import pe.greenminds.ecomind_backend.ranking.interfaces.rest.resources.CreateScoreEntryResource;

public class CreateScoreEntryCommandFromResourceAssembler {
    public static CreateScoreEntryCommand toCommandFromResource(CreateScoreEntryResource resource) {
        return new CreateScoreEntryCommand(
                resource.userId(),
                resource.score(),
                resource.entryType(),
                resource.description()
        );
    }
}
