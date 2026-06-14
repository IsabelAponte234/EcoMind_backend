package pe.greenminds.ecomind_backend.quests.interfaces.rest.transform;

import pe.greenminds.ecomind_backend.quests.domain.model.commands.UpdateQuestCommand;
import pe.greenminds.ecomind_backend.quests.interfaces.rest.resources.UpdateQuestResource;

public final class UpdateQuestCommandFromResourceAssembler {
    private UpdateQuestCommandFromResourceAssembler() {
    }

    public static UpdateQuestCommand toCommandFromResource(
            Long questId,
            UpdateQuestResource resource
    ) {
        return new UpdateQuestCommand(
                questId,
                resource.minigameId(),
                resource.title(),
                resource.description(),
                resource.category(),
                resource.type(),
                resource.gemReward(),
                resource.ecopoints(),
                resource.age(),
                resource.time(),
                resource.theme(),
                resource.assignedDate(),
                resource.image()
        );
    }
}
