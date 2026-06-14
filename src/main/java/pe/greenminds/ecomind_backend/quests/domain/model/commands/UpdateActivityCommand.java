package pe.greenminds.ecomind_backend.quests.domain.model.commands;

import pe.greenminds.ecomind_backend.quests.domain.model.valueobjects.ActivityType;

public record UpdateActivityCommand(
        Long activityId,
        String description,
        Integer order,
        ActivityType type,
        String image
) {
}
