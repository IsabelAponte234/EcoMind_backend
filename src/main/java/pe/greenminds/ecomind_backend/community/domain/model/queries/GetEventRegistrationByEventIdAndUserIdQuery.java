package pe.greenminds.ecomind_backend.community.domain.model.queries;

public record GetEventRegistrationByEventIdAndUserIdQuery(
        Long eventId,
        Long userId
) {
}
