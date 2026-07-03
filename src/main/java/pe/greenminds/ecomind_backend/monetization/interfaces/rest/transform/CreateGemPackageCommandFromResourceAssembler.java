package pe.greenminds.ecomind_backend.monetization.interfaces.rest.transform;

import pe.greenminds.ecomind_backend.monetization.domain.model.commands.CreateGemPackageCommand;
import pe.greenminds.ecomind_backend.monetization.interfaces.rest.resources.CreateGemPackageResource;

public class CreateGemPackageCommandFromResourceAssembler {

    private CreateGemPackageCommandFromResourceAssembler() {}

    public static CreateGemPackageCommand toCommandFromResource(CreateGemPackageResource resource) {
        return new CreateGemPackageCommand(
                resource.name(),
                resource.gemAmount(),
                resource.realPrice(),
                resource.currency()
        );
    }
}
