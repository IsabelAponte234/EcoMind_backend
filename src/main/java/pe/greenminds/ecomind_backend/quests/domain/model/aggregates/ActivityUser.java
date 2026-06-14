package pe.greenminds.ecomind_backend.quests.domain.model.aggregates;

import lombok.Getter;
import lombok.Setter;
import pe.greenminds.ecomind_backend.quests.domain.model.events.ActivityUserCreatedEvent;
import pe.greenminds.ecomind_backend.shared.domain.model.aggregates.AbstractDomainAggregateRoot;

import java.time.LocalDate;
import java.util.Objects;

public class ActivityUser extends AbstractDomainAggregateRoot<ActivityUser> {

    @Getter
    @Setter
    private Long id;

    private Long questUserId;
    private Long activityId;
    private Double progress;
    private LocalDate endDate;
    private Long collaborativeSessionId;

    public ActivityUser(Long id, Long userId, Long activityId,  Double progress, LocalDate endDate, Long collaborativeSessionId) {
        this.id = id;
        this.questUserId = Objects.requireNonNull(userId, "User ID cannot be null");
        this.activityId = Objects.requireNonNull(activityId, "Activity ID cannot be null");
        this.progress = progress;
        this.endDate = endDate;
        this.collaborativeSessionId = collaborativeSessionId;
    }

    public ActivityUser(Long id, Long userId, Long activityId, Long collaborativeSessionId) {
        this.id = id;
        this.questUserId = Objects.requireNonNull(userId, "User ID cannot be null");
        this.activityId = Objects.requireNonNull(activityId, "Activity ID cannot be null");
        this.progress = 0.0;
        this.endDate = null;
        this.collaborativeSessionId = collaborativeSessionId;
    }

    public ActivityUser(Long userId, Long activityId,  Double progress, LocalDate endDate, Long collaborativeSessionId) {
        this(null, userId, activityId, progress, endDate, collaborativeSessionId);
    }

    public ActivityUser(Long userId, Long activityId, Long collaborativeSessionId) {
        this(null, userId, activityId, collaborativeSessionId);
    }

    public void onCreated(){
        registerDomainEvent(ActivityUserCreatedEvent.from(this));
    }

    public void updateProgress(Double progress) {
        if (progress == null || progress < 0 || progress > 100) {
            throw new IllegalArgumentException(
                    "Progress must be between 0 and 100"
            );
        }

        this.progress = progress;

        if (progress >= 100.0) {
            complete();
        }
    }

    private void complete() {
        this.progress = 100.0;
        this.endDate = LocalDate.now();
    }

    public Long getQuestUserId() {
        return questUserId;
    }

    public Long getActivityId() {
        return activityId;
    }

    public Double getProgress() {
        return progress;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Long getCollaborativeSessionId() {
        return collaborativeSessionId;
    }
}
