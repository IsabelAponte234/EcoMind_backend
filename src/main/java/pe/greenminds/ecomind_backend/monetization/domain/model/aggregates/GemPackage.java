package pe.greenminds.ecomind_backend.monetization.domain.model.aggregates;

import lombok.Getter;
import lombok.Setter;
import pe.greenminds.ecomind_backend.monetization.domain.model.events.GemPackageCreatedEvent;
import pe.greenminds.ecomind_backend.shared.domain.model.aggregates.AbstractDomainAggregateRoot;

import java.math.BigDecimal;
import java.util.Objects;

public class GemPackage extends AbstractDomainAggregateRoot<GemPackage> {

    @Getter
    @Setter
    private Long id;

    private String name;
    private Integer gemAmount;
    private BigDecimal realPrice;
    private String currency;

    public GemPackage(Long id, String name, Integer gemAmount, BigDecimal realPrice, String currency) {
        this.id = id;
        this.name = Objects.requireNonNull(name, "name must not be null");
        this.gemAmount = Objects.requireNonNull(gemAmount, "gemAmount must not be null");
        this.realPrice = Objects.requireNonNull(realPrice, "realPrice must not be null");
        this.currency = Objects.requireNonNull(currency, "currency must not be null");
    }

    public GemPackage(String name, Integer gemAmount, BigDecimal realPrice, String currency) {
        this(null, name, gemAmount, realPrice, currency);
    }

    public String getName() { return name; }
    public Integer getGemAmount() { return gemAmount; }
    public BigDecimal getRealPrice() { return realPrice; }
    public String getCurrency() { return currency; }

    public void onCreated() {
        registerDomainEvent(GemPackageCreatedEvent.from(this));
    }
}
