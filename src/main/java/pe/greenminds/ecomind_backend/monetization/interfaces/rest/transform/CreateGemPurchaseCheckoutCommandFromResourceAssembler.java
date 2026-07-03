package pe.greenminds.ecomind_backend.monetization.interfaces.rest.transform;

import pe.greenminds.ecomind_backend.monetization.domain.model.commands.CreateGemPurchaseCheckoutCommand;
import pe.greenminds.ecomind_backend.monetization.domain.model.valueobjects.PaymentMethod;
import pe.greenminds.ecomind_backend.monetization.interfaces.rest.resources.CreateGemPurchaseCheckoutResource;

public class CreateGemPurchaseCheckoutCommandFromResourceAssembler {

    private CreateGemPurchaseCheckoutCommandFromResourceAssembler() {}

    public static CreateGemPurchaseCheckoutCommand toCommandFromResource(CreateGemPurchaseCheckoutResource resource) {
        var paymentMethod = (resource.paymentMethod() == null || resource.paymentMethod().isBlank())
                ? PaymentMethod.CARD
                : PaymentMethod.valueOf(resource.paymentMethod().trim().toUpperCase());
        return new CreateGemPurchaseCheckoutCommand(
                resource.userId(),
                resource.packageId(),
                paymentMethod
        );
    }
}
