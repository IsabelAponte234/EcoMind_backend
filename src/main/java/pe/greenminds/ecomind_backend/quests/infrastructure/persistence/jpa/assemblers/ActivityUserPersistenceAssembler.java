package pe.greenminds.ecomind_backend.quests.infrastructure.persistence.jpa.assemblers;

import pe.greenminds.ecomind_backend.quests.domain.model.aggregates.ActivityUser;
import pe.greenminds.ecomind_backend.quests.infrastructure.persistence.jpa.entities.ActivityUserPersistenceEntity;

public final class ActivityUserPersistenceAssembler {
    private ActivityUserPersistenceAssembler() {
    }

    public static ActivityUser toDomainFromPersistence(ActivityUserPersistenceEntity entity) {
        return new ActivityUser(
                entity.getId(),
                entity.getQuestUserId(),
                entity.getActivityId(),
                entity.getProgress(),
                entity.getEndDate(),
                entity.getCollaborativeSessionId()
        );
    }

    public static ActivityUserPersistenceEntity toPersistenceFromDomain(ActivityUser activityUser) {
        var entity = new ActivityUserPersistenceEntity();
        entity.setId(activityUser.getId());
        entity.setQuestUserId(activityUser.getQuestUserId());
        entity.setActivityId(activityUser.getActivityId());
        entity.setProgress(activityUser.getProgress());
        entity.setEndDate(activityUser.getEndDate());
        entity.setCollaborativeSessionId(activityUser.getCollaborativeSessionId());
        return entity;
    }
}
