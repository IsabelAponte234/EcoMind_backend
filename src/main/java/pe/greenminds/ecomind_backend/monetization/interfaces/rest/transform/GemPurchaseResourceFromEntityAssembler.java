package pe.greenminds.ecomind_backend.monetization.interfaces.rest.transform;

import pe.greenminds.ecomind_backend.monetization.domain.model.aggregates.GemPurchase;
import pe.greenminds.ecomind_backend.monetization.interfaces.rest.resources.GemPurchaseResource;

public class GemPurchaseResourceFromEntityAssembler {

    private GemPurchaseResourceFromEntityAssembler() {}

    public static GemPurchaseResource toResourceFromEntity(GemPurchase gemPurchase) {
        return new GemPurchaseResource(
                gemPurchase.getId(),
                gemPurchase.getUserId(),
                gemPurchase.getPackageId(),
                gemPurchase.getPurchaseDate(),
                gemPurchase.getAmountPaid(),
                gemPurchase.getPaymentStatus().name().toLowerCase(),
                gemPurchase.getPaymentReference()
        );
    }
}
