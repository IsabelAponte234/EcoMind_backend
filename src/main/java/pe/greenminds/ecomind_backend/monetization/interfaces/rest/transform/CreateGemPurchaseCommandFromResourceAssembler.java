package pe.greenminds.ecomind_backend.monetization.interfaces.rest.transform;

import pe.greenminds.ecomind_backend.monetization.domain.model.commands.CreateGemPurchaseCommand;
import pe.greenminds.ecomind_backend.monetization.domain.model.valueobjects.PaymentStatus;
import pe.greenminds.ecomind_backend.monetization.interfaces.rest.resources.CreateGemPurchaseResource;

public class CreateGemPurchaseCommandFromResourceAssembler {

    private CreateGemPurchaseCommandFromResourceAssembler() {}

    public static CreateGemPurchaseCommand toCommandFromResource(CreateGemPurchaseResource resource) {
        return new CreateGemPurchaseCommand(
                resource.userId(),
                resource.packageId(),
                resource.purchaseDate(),
                resource.amountPaid(),
                PaymentStatus.valueOf(resource.paymentStatus().toUpperCase()),
                resource.paymentReference()
        );
    }
}
