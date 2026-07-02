package pe.greenminds.ecomind_backend.quests.application.queryservices;

import pe.greenminds.ecomind_backend.quests.domain.model.queries.GetCollabQuestSessionStateQuery;

public interface CollabQuestSessionQueryService {
    CollabQuestSessionState handle(GetCollabQuestSessionStateQuery query);
}
