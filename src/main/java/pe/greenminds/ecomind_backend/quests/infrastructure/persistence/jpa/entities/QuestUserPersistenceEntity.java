package pe.greenminds.ecomind_backend.quests.infrastructure.persistence.jpa.entities;

import jakarta.persistence.*;
import pe.greenminds.ecomind_backend.quests.domain.model.valueobjects.QuestStatus;
import pe.greenminds.ecomind_backend.shared.infrastructure.persistence.jpa.AuditableAbstractPersistenceEntity;

import java.time.LocalDate;

@Entity
@Table(name = "user_quest")
public class QuestUserPersistenceEntity extends AuditableAbstractPersistenceEntity {
    @Column(name = "user_id",  nullable=false)
    private Long userId;

    @Column(name = "quest_id", nullable=false)
    private Long questId;

    @Enumerated(EnumType.STRING)
    @Column(name="status", nullable=false)
    private QuestStatus status;

    @Column(name="progress", nullable=false)
    private Double progress;

    @Column(name = "endDate")
    private LocalDate endDate;

    @Column(name="collaborative_id")
    private Long collaborativeSessionId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getQuestId() {
        return questId;
    }

    public void setQuestId(Long questId) {
        this.questId = questId;
    }

    public QuestStatus getStatus() {
        return status;
    }

    public void setStatus(QuestStatus status) {
        this.status = status;
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
