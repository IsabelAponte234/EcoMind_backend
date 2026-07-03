package pe.greenminds.ecomind_backend.monetization.interfaces.rest.transform;

import pe.greenminds.ecomind_backend.monetization.domain.model.commands.CreateUserMultiplierCommand;
import pe.greenminds.ecomind_backend.monetization.interfaces.rest.resources.CreateUserMultiplierResource;

public class CreateUserMultiplierCommandFromResourceAssembler {

    private CreateUserMultiplierCommandFromResourceAssembler() {}

    public static CreateUserMultiplierCommand toCommandFromResource(CreateUserMultiplierResource resource) {
        return new CreateUserMultiplierCommand(
                resource.userId(),
                resource.multiplierId(),
                resource.startDate(),
                resource.endDate()
        );
    }
}
