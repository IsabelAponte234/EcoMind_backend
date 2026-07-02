package pe.greenminds.ecomind_backend.quests.interfaces.rest.transform;

import pe.greenminds.ecomind_backend.quests.domain.model.aggregates.CollabQuestSession;
import pe.greenminds.ecomind_backend.quests.interfaces.rest.resources.CollabQuestSessionResource;

import java.util.Locale;

public final class CollabQuestSessionResourceFromEntityAssembler {
    private CollabQuestSessionResourceFromEntityAssembler() {
    }

    public static CollabQuestSessionResource toResourceFromEntity(
            CollabQuestSession collabQuestSession
    ) {
        if (collabQuestSession == null) {
            return null;
        }

        return new CollabQuestSessionResource(
                collabQuestSession.getId(),
                collabQuestSession.getQuestId(),
                collabQuestSession.getOwnerId(),
                collabQuestSession.getStatus().name().toLowerCase(Locale.ROOT),
                collabQuestSession.getCreatedAt(),
                collabQuestSession.getStartDate(),
                collabQuestSession.getEndDate()
        );
    }
}
