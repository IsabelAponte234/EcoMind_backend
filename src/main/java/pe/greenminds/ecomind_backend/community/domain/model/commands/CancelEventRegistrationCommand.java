package pe.greenminds.ecomind_backend.community.domain.model.commands;

public record CancelEventRegistrationCommand(
        Long eventId,
        Long registrationId
) {
}
