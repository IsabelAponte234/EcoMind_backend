package pe.greenminds.ecomind_backend.profile.application.queryservices;

import pe.greenminds.ecomind_backend.profile.domain.model.aggregates.Friend;
import pe.greenminds.ecomind_backend.profile.domain.model.queries.GetFriendsQuery;

import java.util.List;

public interface FriendQueryService {
    List<Friend> handle(GetFriendsQuery query);
}
