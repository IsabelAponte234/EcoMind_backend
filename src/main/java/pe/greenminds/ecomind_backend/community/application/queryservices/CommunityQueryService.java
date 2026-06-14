package pe.greenminds.ecomind_backend.community.application.queryservices;

import pe.greenminds.ecomind_backend.community.domain.model.aggregates.Community;
import pe.greenminds.ecomind_backend.community.domain.model.queries.SearchCommunitiesQuery;

import java.util.List;

public interface CommunityQueryService {
    List<Community> handle(SearchCommunitiesQuery query);
}
