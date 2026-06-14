package pe.greenminds.ecomind_backend.community.interfaces.rest.transform;

import pe.greenminds.ecomind_backend.community.domain.model.commands.CreateEventCommand;
import pe.greenminds.ecomind_backend.community.interfaces.rest.resources.CreateEventResource;

public class CreateEventCommandFromResourceAssembler {

    private CreateEventCommandFromResourceAssembler() {}

    public static CreateEventCommand toCommandFromResource(CreateEventResource resource) {
        return new CreateEventCommand(
                resource.communityId(),
                resource.authorId(),
                resource.name(),
                resource.description(),
                resource.date(),
                resource.startTime(),
                resource.endTime(),
                resource.location(),
                resource.latitude(),
                resource.longitude(),
                resource.capacity(),
                resource.imageUrl()
        );
    }
}