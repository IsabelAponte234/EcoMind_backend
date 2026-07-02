package pe.greenminds.ecomind_backend.quests.interfaces.rest.resources;

import java.util.List;

public record CollabQuestSessionStateResource(
        CollabQuestSessionResource session,
        List<CollabQuestMemberResource> members
) {
}
