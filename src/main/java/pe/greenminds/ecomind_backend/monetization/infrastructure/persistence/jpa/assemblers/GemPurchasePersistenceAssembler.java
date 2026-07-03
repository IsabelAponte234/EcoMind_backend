package pe.greenminds.ecomind_backend.monetization.infrastructure.persistence.jpa.assemblers;

import pe.greenminds.ecomind_backend.monetization.domain.model.aggregates.GemPurchase;
import pe.greenminds.ecomind_backend.monetization.domain.model.valueobjects.PaymentMethod;
import pe.greenminds.ecomind_backend.monetization.infrastructure.persistence.jpa.entities.GemPurchasePersistenceEntity;

public class GemPurchasePersistenceAssembler {

    private GemPurchasePersistenceAssembler() {}

    public static GemPurchase toDomainFromPersistence(GemPurchasePersistenceEntity entity) {
        var paymentMethod = entity.getPaymentMethod() != null ? entity.getPaymentMethod() : PaymentMethod.CARD;
        var gemPurchase = new GemPurchase(
                entity.getUserId(),
                entity.getPackageId(),
                entity.getPurchaseDate(),
                entity.getAmountPaid(),
                entity.getPaymentStatus(),
                entity.getPaymentReference(),
                paymentMethod
        );
        gemPurchase.setId(entity.getId());
        return gemPurchase;
    }

    public static GemPurchasePersistenceEntity toPersistenceFromDomain(GemPurchase gemPurchase) {
        var entity = new GemPurchasePersistenceEntity();
        entity.setId(gemPurchase.getId());
        entity.setUserId(gemPurchase.getUserId());
        entity.setPackageId(gemPurchase.getPackageId());
        entity.setPurchaseDate(gemPurchase.getPurchaseDate());
        entity.setAmountPaid(gemPurchase.getAmountPaid());
        entity.setPaymentStatus(gemPurchase.getPaymentStatus());
        entity.setPaymentReference(gemPurchase.getPaymentReference());
        entity.setPaymentMethod(gemPurchase.getPaymentMethod());
        return entity;
    }
}
