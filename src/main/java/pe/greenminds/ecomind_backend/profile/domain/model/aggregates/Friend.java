package pe.greenminds.ecomind_backend.profile.domain.model.aggregates;

import lombok.Getter;
import lombok.Setter;
import pe.greenminds.ecomind_backend.profile.domain.model.valueobjects.FriendStatus;
import pe.greenminds.ecomind_backend.shared.domain.model.aggregates.AbstractDomainAggregateRoot;

import java.util.Objects;

public class Friend extends AbstractDomainAggregateRoot<Friend> {
    @Getter
    @Setter
    private Long id;

    @Getter
    private Long userId;
    @Getter
    private Long friendId;
    @Getter
    private FriendStatus status;

    public Friend(Long id, Long userId, Long friendId, FriendStatus status) {
        this.id = id;
        this.userId = Objects.requireNonNull(userId, "userId must not be null");
        this.friendId = Objects.requireNonNull(friendId, "friendId must not be null");
        this.status = status == null ? FriendStatus.PENDING : status;
    }

    public void update(Long userId, Long friendId, FriendStatus status) {
        this.userId = Objects.requireNonNull(userId, "userId must not be null");
        this.friendId = Objects.requireNonNull(friendId, "friendId must not be null");
        this.status = status == null ? this.status : status;
    }

    public void accept() {
        this.status = FriendStatus.ACCEPTED;
    }

    public void reject() {
        this.status = FriendStatus.REJECTED;
    }
}
