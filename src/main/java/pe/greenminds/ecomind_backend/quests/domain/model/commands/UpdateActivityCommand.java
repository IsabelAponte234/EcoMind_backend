package pe.greenminds.ecomind_backend.quests.domain.model.commands;

import pe.greenminds.ecomind_backend.quests.domain.model.valueobjects.ActivityType;

import java.util.Map;

public record UpdateActivityCommand(
        Long activityId,
        String description,
        Integer order,
        ActivityType type,
        Map<String, Object> activityConfiguration,
        String image
) {
}
