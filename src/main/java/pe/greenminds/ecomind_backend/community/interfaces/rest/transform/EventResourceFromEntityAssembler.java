package pe.greenminds.ecomind_backend.community.interfaces.rest.transform;

import pe.greenminds.ecomind_backend.community.domain.model.aggregates.Event;
import pe.greenminds.ecomind_backend.community.interfaces.rest.resources.EventResource;

public class EventResourceFromEntityAssembler {

    private EventResourceFromEntityAssembler() {}

    public static EventResource toResourceFromEntity(Event event) {
        return new EventResource(
                event.getId(),
                event.getCommunityId(),
                event.getAuthorId(),
                event.getName(),
                event.getDescription(),
                event.getDate(),
                event.getStartTime(),
                event.getEndTime(),
                event.getLocation(),
                event.getLatitude(),
                event.getLongitude(),
                event.getCapacity(),
                event.getImageUrl()
        );
    }
}