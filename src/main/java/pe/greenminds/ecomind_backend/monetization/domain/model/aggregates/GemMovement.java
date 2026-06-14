package pe.greenminds.ecomind_backend.monetization.domain.model.aggregates;

import lombok.Getter;
import lombok.Setter;
import pe.greenminds.ecomind_backend.monetization.domain.model.events.GemMovementCreatedEvent;
import pe.greenminds.ecomind_backend.monetization.domain.model.valueobjects.MovementOrigin;
import pe.greenminds.ecomind_backend.monetization.domain.model.valueobjects.MovementType;
import pe.greenminds.ecomind_backend.shared.domain.model.aggregates.AbstractDomainAggregateRoot;

import java.util.Objects;

public class GemMovement extends AbstractDomainAggregateRoot<GemMovement> {

    @Getter
    @Setter
    private Long id;

    private Long userId;
    private MovementType type;
    private Integer amount;
    private MovementOrigin origin;
    private Long originId;

    public GemMovement(Long id, Long userId, MovementType type, Integer amount, MovementOrigin origin, Long originId) {
        this.id = id;
        this.userId = Objects.requireNonNull(userId, "userId must not be null");
        this.type = Objects.requireNonNull(type, "type must not be null");
        this.amount = Objects.requireNonNull(amount, "amount must not be null");
        this.origin = Objects.requireNonNull(origin, "origin must not be null");
        this.originId = originId;
    }

    public GemMovement(Long userId, MovementType type, Integer amount, MovementOrigin origin, Long originId) {
        this(null, userId, type, amount, origin, originId);
    }

    public Long getUserId() { return userId; }
    public MovementType getType() { return type; }
    public Integer getAmount() { return amount; }
    public MovementOrigin getOrigin() { return origin; }
    public Long getOriginId() { return originId; }

    public void onCreated() {
        registerDomainEvent(GemMovementCreatedEvent.from(this));
    }
}
