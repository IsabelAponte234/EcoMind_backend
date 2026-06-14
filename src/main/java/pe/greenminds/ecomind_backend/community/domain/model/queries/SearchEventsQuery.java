package pe.greenminds.ecomind_backend.community.domain.model.queries;

import java.time.LocalDate;

public record SearchEventsQuery(
        Long communityId,
        Long authorId,
        String name,
        LocalDate date,
        String location
) {
}