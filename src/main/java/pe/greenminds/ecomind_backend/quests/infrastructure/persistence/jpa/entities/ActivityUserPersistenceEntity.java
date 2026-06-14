package pe.greenminds.ecomind_backend.quests.infrastructure.persistence.jpa.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import pe.greenminds.ecomind_backend.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;

import java.time.LocalDate;

@Entity
@Table(name = "activity_user")
public class ActivityUserPersistenceEntity extends AuditableAbstractPersistenceEntity {
    @Column(name="questUser_id", nullable = false)
    private Long questUserId;

    @Column(name="activity_id", nullable = false)
    private Long activityId;

    @Column(name="progress", nullable = false)
    private Double progress;

    @Column(name="endDate")
    private LocalDate endDate;

    @Column(name="collaborativeSession_id")
    private Long collaborativeSessionId;

    public Long getQuestUserId() {
        return questUserId;
    }

    public void setQuestUserId(Long userId) {
        this.questUserId = userId;
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public Double getProgress() {
        return progress;
    }

    public void setProgress(Double progress) {
        this.progress = progress;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Long getCollaborativeSessionId() {
        return collaborativeSessionId;
    }

    public void setCollaborativeSessionId(Long collaborativeSessionId) {
        this.collaborativeSessionId = collaborativeSessionId;
    }
}
