package pe.greenminds.ecomind_backend.monetization.interfaces.rest.transform;

import pe.greenminds.ecomind_backend.monetization.domain.model.commands.UpdateUserCosmeticCommand;
import pe.greenminds.ecomind_backend.monetization.interfaces.rest.resources.UpdateUserCosmeticResource;

public class UpdateUserCosmeticCommandFromResourceAssembler {

    private UpdateUserCosmeticCommandFromResourceAssembler() {}

    public static UpdateUserCosmeticCommand toCommandFromResource(Long userCosmeticId, UpdateUserCosmeticResource resource) {
        return new UpdateUserCosmeticCommand(
                userCosmeticId,
                resource.userId(),
                resource.cosmeticId(),
                resource.acquiredAt(),
                resource.equipped()
        );
    }
}
