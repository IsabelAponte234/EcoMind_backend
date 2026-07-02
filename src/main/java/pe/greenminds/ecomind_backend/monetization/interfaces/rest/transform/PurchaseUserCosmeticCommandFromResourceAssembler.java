package pe.greenminds.ecomind_backend.monetization.interfaces.rest.transform;

import pe.greenminds.ecomind_backend.monetization.domain.model.commands.PurchaseUserCosmeticCommand;
import pe.greenminds.ecomind_backend.monetization.interfaces.rest.resources.PurchaseUserCosmeticResource;

public class PurchaseUserCosmeticCommandFromResourceAssembler {

    private PurchaseUserCosmeticCommandFromResourceAssembler() {}

    public static PurchaseUserCosmeticCommand toCommandFromResource(PurchaseUserCosmeticResource resource) {
        return new PurchaseUserCosmeticCommand(
                resource.userId(),
                resource.cosmeticId()
        );
    }
}
