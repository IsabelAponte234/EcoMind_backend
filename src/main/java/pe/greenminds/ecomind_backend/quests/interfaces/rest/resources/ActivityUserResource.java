package pe.greenminds.ecomind_backend.quests.interfaces.rest.resources;

import java.time.LocalDate;

public record ActivityUserResource(
        Long id,
        Long questUserId,
        Long activityId,
        Double progress,
        LocalDate endDate,
        Long collaborativeSessionId
) {
}
