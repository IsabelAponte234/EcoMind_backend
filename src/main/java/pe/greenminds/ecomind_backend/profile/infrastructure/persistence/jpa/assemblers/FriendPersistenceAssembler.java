package pe.greenminds.ecomind_backend.profile.infrastructure.persistence.jpa.assemblers;

import pe.greenminds.ecomind_backend.profile.domain.model.aggregates.Friend;
import pe.greenminds.ecomind_backend.profile.infrastructure.persistence.jpa.entities.FriendPersistenceEntity;

public class FriendPersistenceAssembler {
    private FriendPersistenceAssembler() {}

    public static Friend toDomainFromPersistence(FriendPersistenceEntity entity) {
        return new Friend(entity.getId(), entity.getUserId(), entity.getFriendId(), entity.getStatus());
    }

    public static FriendPersistenceEntity toPersistenceFromDomain(Friend friend) {
        var entity = new FriendPersistenceEntity();
        entity.setId(friend.getId());
        entity.setUserId(friend.getUserId());
        entity.setFriendId(friend.getFriendId());
        entity.setStatus(friend.getStatus());
        return entity;
    }
}
