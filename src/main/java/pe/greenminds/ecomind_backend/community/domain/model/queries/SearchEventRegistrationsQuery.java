package pe.greenminds.ecomind_backend.community.domain.model.queries;

import pe.greenminds.ecomind_backend.community.domain.model.valueobjects.EventRegistrationStatus;

public record SearchEventRegistrationsQuery(
        Long eventId,
        Long userId,
        Long familyId,
        EventRegistrationStatus status
) {

}
