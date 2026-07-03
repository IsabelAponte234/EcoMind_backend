package pe.greenminds.ecomind_backend.monetization.domain.model.aggregates;

import lombok.Getter;
import lombok.Setter;
import pe.greenminds.ecomind_backend.monetization.domain.model.events.GemPurchaseCreatedEvent;
import pe.greenminds.ecomind_backend.monetization.domain.model.valueobjects.PaymentMethod;
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
    private PaymentMethod paymentMethod;

    public GemPurchase(Long id, Long userId, Long packageId, LocalDateTime purchaseDate, BigDecimal amountPaid, PaymentStatus paymentStatus, String paymentReference, PaymentMethod paymentMethod) {
        this.id = id;
        this.userId = Objects.requireNonNull(userId, "userId must not be null");
        this.packageId = Objects.requireNonNull(packageId, "packageId must not be null");
        this.purchaseDate = Objects.requireNonNull(purchaseDate, "purchaseDate must not be null");
        this.amountPaid = Objects.requireNonNull(amountPaid, "amountPaid must not be null");
        this.paymentStatus = Objects.requireNonNull(paymentStatus, "paymentStatus must not be null");
        this.paymentReference = paymentReference;
        this.paymentMethod = Objects.requireNonNull(paymentMethod, "paymentMethod must not be null");
    }

    public GemPurchase(Long userId, Long packageId, LocalDateTime purchaseDate, BigDecimal amountPaid, PaymentStatus paymentStatus, String paymentReference, PaymentMethod paymentMethod) {
        this(null, userId, packageId, purchaseDate, amountPaid, paymentStatus, paymentReference, paymentMethod);
    }

    public Long getUserId() { return userId; }
    public Long getPackageId() { return packageId; }
    public LocalDateTime getPurchaseDate() { return purchaseDate; }
    public BigDecimal getAmountPaid() { return amountPaid; }
    public PaymentStatus getPaymentStatus() { return paymentStatus; }
    public String getPaymentReference() { return paymentReference; }
    public PaymentMethod getPaymentMethod() { return paymentMethod; }

    public void onCreated() {
        registerDomainEvent(GemPurchaseCreatedEvent.from(this));
    }

    public void approve() {
        if (this.paymentStatus != PaymentStatus.PENDING) {
            throw new IllegalStateException("Gem purchase must be PENDING to be approved");
        }
        this.paymentStatus = PaymentStatus.APPROVED;
    }

    public void reject() {
        if (this.paymentStatus != PaymentStatus.PENDING) {
            throw new IllegalStateException("Gem purchase must be PENDING to be rejected");
        }
        this.paymentStatus = PaymentStatus.REJECTED;
    }

    public void assignPaymentReference(String paymentReference) {
        this.paymentReference = paymentReference;
    }
}
