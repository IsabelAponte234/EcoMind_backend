package pe.greenminds.ecomind_backend.monetization.interfaces.rest.transform;

import pe.greenminds.ecomind_backend.monetization.domain.model.commands.CreateUserCosmeticCommand;
import pe.greenminds.ecomind_backend.monetization.interfaces.rest.resources.CreateUserCosmeticResource;

public class CreateUserCosmeticCommandFromResourceAssembler {

    private CreateUserCosmeticCommandFromResourceAssembler() {}

    public static CreateUserCosmeticCommand toCommandFromResource(CreateUserCosmeticResource resource) {
        return new CreateUserCosmeticCommand(
                resource.userId(),
                resource.cosmeticId(),
                resource.acquiredAt(),
                resource.equipped()
        );
    }
}
