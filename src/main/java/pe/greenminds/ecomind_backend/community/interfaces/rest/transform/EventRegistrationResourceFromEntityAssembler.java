package pe.greenminds.ecomind_backend.community.interfaces.rest.transform;

import pe.greenminds.ecomind_backend.community.domain.model.aggregates.EventRegistration;
import pe.greenminds.ecomind_backend.community.interfaces.rest.resources.EventRegistrationResource;

public class EventRegistrationResourceFromEntityAssembler {

    private EventRegistrationResourceFromEntityAssembler() {}

    public static EventRegistrationResource toResourceFromEntity(EventRegistration registration) {
        return new EventRegistrationResource(
                registration.getId(),
                registration.getEventId(),
                registration.getUserId(),
                registration.getFamilyId(),
                registration.getRegistrationType(),
                registration.getRegisteredAt(),
                registration.getStatus()
        );
    }
}