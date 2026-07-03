package pe.greenminds.ecomind_backend.monetization.domain.model.aggregates;

import lombok.Getter;
import lombok.Setter;
import pe.greenminds.ecomind_backend.monetization.domain.model.events.UserMultiplierCreatedEvent;
import pe.greenminds.ecomind_backend.shared.domain.model.aggregates.AbstractDomainAggregateRoot;

import java.time.LocalDateTime;
import java.util.Objects;

public class UserMultiplier extends AbstractDomainAggregateRoot<UserMultiplier> {

    @Getter
    @Setter
    private Long id;

    private Long userId;
    private Long multiplierId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public UserMultiplier(Long id, Long userId, Long multiplierId, LocalDateTime startDate, LocalDateTime endDate) {
        this.id = id;
        this.userId = Objects.requireNonNull(userId, "userId must not be null");
        this.multiplierId = Objects.requireNonNull(multiplierId, "multiplierId must not be null");
        this.startDate = Objects.requireNonNull(startDate, "startDate must not be null");
        this.endDate = Objects.requireNonNull(endDate, "endDate must not be null");
    }

    public UserMultiplier(Long userId, Long multiplierId, LocalDateTime startDate, LocalDateTime endDate) {
        this(null, userId, multiplierId, startDate, endDate);
    }

    public Long getUserId() { return userId; }
    public Long getMultiplierId() { return multiplierId; }
    public LocalDateTime getStartDate() { return startDate; }
    public LocalDateTime getEndDate() { return endDate; }

    public void onCreated() {
        registerDomainEvent(UserMultiplierCreatedEvent.from(this));
    }
}
