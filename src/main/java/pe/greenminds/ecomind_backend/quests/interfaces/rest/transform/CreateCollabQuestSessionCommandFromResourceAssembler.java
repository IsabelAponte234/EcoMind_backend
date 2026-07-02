package pe.greenminds.ecomind_backend.quests.interfaces.rest.transform;

import pe.greenminds.ecomind_backend.quests.domain.model.commands.CreateCollabQuestSessionCommand;
import pe.greenminds.ecomind_backend.quests.interfaces.rest.resources.CreateCollabQuestSessionResource;

public final class CreateCollabQuestSessionCommandFromResourceAssembler {
    private CreateCollabQuestSessionCommandFromResourceAssembler() {
    }

    public static CreateCollabQuestSessionCommand toCommandFromResource(
            CreateCollabQuestSessionResource resource
    ) {
        return new CreateCollabQuestSessionCommand(
                resource.questId(),
                resource.ownerUserId()
        );
    }
}
