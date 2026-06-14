package pe.greenminds.ecomind_backend.monetization.domain.model.aggregates;

import lombok.Getter;
import lombok.Setter;
import pe.greenminds.ecomind_backend.monetization.domain.model.events.UserCosmeticCreatedEvent;
import pe.greenminds.ecomind_backend.shared.domain.model.aggregates.AbstractDomainAggregateRoot;

import java.time.LocalDateTime;
import java.util.Objects;

public class UserCosmetic extends AbstractDomainAggregateRoot<UserCosmetic> {

    @Getter
    @Setter
    private Long id;

    private Long userId;
    private Long cosmeticId;
    private LocalDateTime acquiredAt;
    private Boolean equipped;

    public UserCosmetic(Long id, Long userId, Long cosmeticId, LocalDateTime acquiredAt, Boolean equipped) {
        this.id = id;
        this.userId = Objects.requireNonNull(userId, "userId must not be null");
        this.cosmeticId = Objects.requireNonNull(cosmeticId, "cosmeticId must not be null");
        this.acquiredAt = Objects.requireNonNull(acquiredAt, "acquiredAt must not be null");
        this.equipped = Objects.requireNonNull(equipped, "equipped must not be null");
    }

    public UserCosmetic(Long userId, Long cosmeticId, LocalDateTime acquiredAt, Boolean equipped) {
        this(null, userId, cosmeticId, acquiredAt, equipped);
    }

    public Long getUserId() { return userId; }
    public Long getCosmeticId() { return cosmeticId; }
    public LocalDateTime getAcquiredAt() { return acquiredAt; }
    public Boolean getEquipped() { return equipped; }

    public void onCreated() {
        registerDomainEvent(UserCosmeticCreatedEvent.from(this));
    }
}
