package pe.greenminds.ecomind_backend.profile.infrastructure.persistence.jpa.entities;

import jakarta.persistence.*;
import pe.greenminds.ecomind_backend.profile.domain.model.valueobjects.FriendStatus;
import pe.greenminds.ecomind_backend.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;

@Entity
@Table(name = "friends")
public class FriendPersistenceEntity extends AuditableAbstractPersistenceEntity {
    @Column(name = "user_id", nullable = false)
    private Long userId;
    @Column(name = "friend_id", nullable = false)
    private Long friendId;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FriendStatus status;

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public Long getFriendId() { return friendId; }
    public void setFriendId(Long friendId) { this.friendId = friendId; }
    public FriendStatus getStatus() { return status; }
    public void setStatus(FriendStatus status) { this.status = status; }
}
