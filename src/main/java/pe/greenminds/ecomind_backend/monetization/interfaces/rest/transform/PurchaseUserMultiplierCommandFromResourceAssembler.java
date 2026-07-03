package pe.greenminds.ecomind_backend.monetization.interfaces.rest.transform;

import pe.greenminds.ecomind_backend.monetization.domain.model.commands.PurchaseUserMultiplierCommand;
import pe.greenminds.ecomind_backend.monetization.interfaces.rest.resources.PurchaseUserMultiplierResource;

public class PurchaseUserMultiplierCommandFromResourceAssembler {

    private PurchaseUserMultiplierCommandFromResourceAssembler() {}

    public static PurchaseUserMultiplierCommand toCommandFromResource(PurchaseUserMultiplierResource resource) {
        return new PurchaseUserMultiplierCommand(
                resource.userId(),
                resource.multiplierId()
        );
    }
}
