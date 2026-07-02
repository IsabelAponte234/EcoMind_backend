package pe.greenminds.ecomind_backend.quests.interfaces.rest.transform;

import pe.greenminds.ecomind_backend.quests.application.queryservices.CollabQuestSessionState;
import pe.greenminds.ecomind_backend.quests.interfaces.rest.resources.CollabQuestSessionStateResource;

import java.util.List;

public final class CollabQuestSessionStateResourceAssembler {
    private CollabQuestSessionStateResourceAssembler() {
    }

    public static CollabQuestSessionStateResource toResource(
            CollabQuestSessionState state
    ) {
        var memberResources = state.members()
                .stream()
                .map(CollabQuestMemberResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return new CollabQuestSessionStateResource(
                CollabQuestSessionResourceFromEntityAssembler.toResourceFromEntity(
                        state.session()
                ),
                memberResources
        );
    }
}
