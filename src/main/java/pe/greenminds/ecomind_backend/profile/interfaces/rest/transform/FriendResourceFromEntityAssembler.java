package pe.greenminds.ecomind_backend.profile.interfaces.rest.transform;

import pe.greenminds.ecomind_backend.profile.domain.model.aggregates.Friend;
import pe.greenminds.ecomind_backend.profile.interfaces.rest.resources.FriendResource;

public class FriendResourceFromEntityAssembler {
    private FriendResourceFromEntityAssembler() {}

    public static FriendResource toResourceFromEntity(Friend friend) {
        return new FriendResource(friend.getId(), friend.getUserId(), friend.getFriendId(), friend.getStatus());
    }
}
