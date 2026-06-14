package pe.greenminds.ecomind_backend.quests.application.internal.submission;

import pe.greenminds.ecomind_backend.quests.domain.model.aggregates.ActivityUser;
import pe.greenminds.ecomind_backend.quests.domain.model.valueobjects.ActivityType;

import java.util.Map;

public interface ActivitySubmissionHandler {
    ActivityType supportedType();

    void submit(ActivityUser activityUser, Map<String, Object> data);
}
