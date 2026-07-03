package pe.greenminds.ecomind_backend.monetization.interfaces.rest.transform;

import pe.greenminds.ecomind_backend.monetization.domain.model.commands.PayGemPurchaseCommand;
import pe.greenminds.ecomind_backend.monetization.interfaces.rest.resources.PayGemPurchaseResource;

public class PayGemPurchaseCommandFromResourceAssembler {

    private PayGemPurchaseCommandFromResourceAssembler() {}

    public static PayGemPurchaseCommand toCommandFromResource(Long gemPurchaseId, PayGemPurchaseResource resource) {
        return new PayGemPurchaseCommand(
                gemPurchaseId,
                resource.sourceToken(),
                resource.email()
        );
    }
}
