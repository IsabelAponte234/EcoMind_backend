package pe.greenminds.ecomind_backend.community.interfaces.rest.transform;

import pe.greenminds.ecomind_backend.community.domain.model.commands.CreateEventRegistrationCommand;
import pe.greenminds.ecomind_backend.community.interfaces.rest.resources.CreateEventRegistrationResource;

public class CreateEventRegistrationCommandFromResourceAssembler {

    private CreateEventRegistrationCommandFromResourceAssembler() {}

    public static CreateEventRegistrationCommand toCommandFromResource(
            Long eventId,
            CreateEventRegistrationResource resource
    ) {
        return new CreateEventRegistrationCommand(
                eventId,
                resource.userId(),
                resource.familyId(),
                resource.registrationType()
        );
    }
}