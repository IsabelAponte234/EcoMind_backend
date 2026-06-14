package pe.greenminds.ecomind_backend.monetization.interfaces.rest.transform;

import pe.greenminds.ecomind_backend.monetization.domain.model.commands.CreateGemMovementCommand;
import pe.greenminds.ecomind_backend.monetization.domain.model.valueobjects.MovementOrigin;
import pe.greenminds.ecomind_backend.monetization.domain.model.valueobjects.MovementType;
import pe.greenminds.ecomind_backend.monetization.interfaces.rest.resources.CreateGemMovementResource;

public class CreateGemMovementCommandFromResourceAssembler {

    private CreateGemMovementCommandFromResourceAssembler() {}

    public static CreateGemMovementCommand toCommandFromResource(CreateGemMovementResource resource) {
        return new CreateGemMovementCommand(
                resource.userId(),
                MovementType.valueOf(resource.type().toUpperCase()),
                resource.amount(),
                MovementOrigin.valueOf(resource.origin().toUpperCase()),
                resource.originId()
        );
    }
}
