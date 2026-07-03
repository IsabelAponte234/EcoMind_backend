package pe.greenminds.ecomind_backend.monetization.infrastructure.persistence.jpa.entities;

import jakarta.persistence.*;
import pe.greenminds.ecomind_backend.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;

import java.math.BigDecimal;

@Entity
@Table(name = "gem_packages")
public class GemPackagePersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "gem_amount", nullable = false)
    private Integer gemAmount;

    @Column(name = "real_price", nullable = false)
    private BigDecimal realPrice;

    @Column(name = "currency", nullable = false)
    private String currency;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Integer getGemAmount() { return gemAmount; }
    public void setGemAmount(Integer gemAmount) { this.gemAmount = gemAmount; }

    public BigDecimal getRealPrice() { return realPrice; }
    public void setRealPrice(BigDecimal realPrice) { this.realPrice = realPrice; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
}
