package pe.greenminds.ecomind_backend.quests.application.queryservices;

import pe.greenminds.ecomind_backend.quests.domain.model.aggregates.CollabQuestMember;
import pe.greenminds.ecomind_backend.quests.domain.model.aggregates.CollabQuestSession;

import java.util.List;

public record CollabQuestSessionState(
        CollabQuestSession session,
        List<CollabQuestMember> members
) {
}
