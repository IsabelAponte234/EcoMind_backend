package pe.greenminds.ecomind_backend.monetization.domain.model.aggregates;

import lombok.Getter;
import lombok.Setter;
import pe.greenminds.ecomind_backend.monetization.domain.model.events.GemPurchaseCreatedEvent;
import pe.greenminds.ecomind_backend.monetization.domain.model.valueobjects.PaymentStatus;
import pe.greenminds.ecomind_backend.shared.domain.model.aggregates.AbstractDomainAggregateRoot;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class GemPurchase extends AbstractDomainAggregateRoot<GemPurchase> {

    @Getter
    @Setter
    private Long id;

    private Long userId;
    private Long packageId;
    private LocalDateTime purchaseDate;
    private BigDecimal amountPaid;
    private PaymentStatus paymentStatus;
    private String paymentReference;

    public GemPurchase(Long id, Long userId, Long packageId, LocalDateTime purchaseDate, BigDecimal amountPaid, PaymentStatus paymentStatus, String paymentReference) {
        this.id = id;
        this.userId = Objects.requireNonNull(userId, "userId must not be null");
        this.packageId = Objects.requireNonNull(packageId, "packageId must not be null");
        this.purchaseDate = Objects.requireNonNull(purchaseDate, "purchaseDate must not be null");
        this.amountPaid = Objects.requireNonNull(amountPaid, "amountPaid must not be null");
        this.paymentStatus = Objects.requireNonNull(paymentStatus, "paymentStatus must not be null");
        this.paymentReference = paymentReference;
    }

    public GemPurchase(Long userId, Long packageId, LocalDateTime purchaseDate, BigDecimal amountPaid, PaymentStatus paymentStatus, String paymentReference) {
        this(null, userId, packageId, purchaseDate, amountPaid, paymentStatus, paymentReference);
    }

    public Long getUserId() { return userId; }
    public Long getPackageId() { return packageId; }
    public LocalDateTime getPurchaseDate() { return purchaseDate; }
    public BigDecimal getAmountPaid() { return amountPaid; }
    public PaymentStatus getPaymentStatus() { return paymentStatus; }
    public String getPaymentReference() { return paymentReference; }

    public void onCreated() {
        registerDomainEvent(GemPurchaseCreatedEvent.from(this));
    }
}
