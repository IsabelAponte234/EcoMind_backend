package pe.greenminds.ecomind_backend.community.domain.model.commands;

import pe.greenminds.ecomind_backend.community.domain.model.valueobjects.EventRegistrationType;

public record CreateEventRegistrationCommand(
        Long eventId,
        Long userId,
        Long familyId,
        EventRegistrationType registrationType
) {
}
