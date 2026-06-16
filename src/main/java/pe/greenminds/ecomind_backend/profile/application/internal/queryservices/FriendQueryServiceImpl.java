package pe.greenminds.ecomind_backend.profile.application.internal.queryservices;

import org.springframework.stereotype.Service;
import pe.greenminds.ecomind_backend.profile.application.queryservices.FriendQueryService;
import pe.greenminds.ecomind_backend.profile.domain.model.aggregates.Friend;
import pe.greenminds.ecomind_backend.profile.domain.model.queries.GetFriendsQuery;
import pe.greenminds.ecomind_backend.profile.domain.repositories.FriendRepository;

import java.util.List;

@Service
public class FriendQueryServiceImpl implements FriendQueryService {
    private final FriendRepository repository;

    public FriendQueryServiceImpl(FriendRepository repository) {
        this.repository = repository;
    }

    public List<Friend> handle(GetFriendsQuery query) {
        return repository.search(query.userId(), query.status());
    }
}
