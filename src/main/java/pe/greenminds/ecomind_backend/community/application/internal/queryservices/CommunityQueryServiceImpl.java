package pe.greenminds.ecomind_backend.community.application.internal.queryservices;

import org.springframework.stereotype.Service;
import pe.greenminds.ecomind_backend.community.application.queryservices.CommunityQueryService;
import pe.greenminds.ecomind_backend.community.domain.model.aggregates.Community;
import pe.greenminds.ecomind_backend.community.domain.model.queries.SearchCommunitiesQuery;
import pe.greenminds.ecomind_backend.community.domain.repositories.CommunityRepository;

import java.util.List;

@Service
public class CommunityQueryServiceImpl implements CommunityQueryService {
    private final CommunityRepository communityRepository;

    public CommunityQueryServiceImpl(CommunityRepository communityRepository) {
        this.communityRepository = communityRepository;
    }

    @Override
    public List<Community> handle(SearchCommunitiesQuery query) {
        return communityRepository.search(
                query.name(),
                query.location()
        );
    }
}
