package pe.greenminds.ecomind_backend.monetization.interfaces.rest.transform;

import pe.greenminds.ecomind_backend.monetization.domain.model.commands.CreateGemPurchaseCommand;
import pe.greenminds.ecomind_backend.monetization.domain.model.valueobjects.PaymentMethod;
import pe.greenminds.ecomind_backend.monetization.domain.model.valueobjects.PaymentStatus;
import pe.greenminds.ecomind_backend.monetization.interfaces.rest.resources.CreateGemPurchaseResource;

public class CreateGemPurchaseCommandFromResourceAssembler {

    private CreateGemPurchaseCommandFromResourceAssembler() {}

    public static CreateGemPurchaseCommand toCommandFromResource(CreateGemPurchaseResource resource) {
        var paymentMethod = (resource.paymentMethod() == null || resource.paymentMethod().isBlank())
                ? PaymentMethod.CARD
                : PaymentMethod.valueOf(resource.paymentMethod().trim().toUpperCase());
        return new CreateGemPurchaseCommand(
                resource.userId(),
                resource.packageId(),
                resource.purchaseDate(),
                resource.amountPaid(),
                PaymentStatus.valueOf(resource.paymentStatus().toUpperCase()),
                resource.paymentReference(),
                paymentMethod
        );
    }
}
