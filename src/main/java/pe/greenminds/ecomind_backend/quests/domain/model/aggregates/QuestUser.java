package pe.greenminds.ecomind_backend.quests.domain.model.aggregates;

import lombok.Getter;
import lombok.Setter;
import pe.greenminds.ecomind_backend.quests.domain.model.events.QuestUserCreatedEvent;
import pe.greenminds.ecomind_backend.quests.domain.model.valueobjects.QuestStatus;
import pe.greenminds.ecomind_backend.shared.domain.model.aggregates.AbstractDomainAggregateRoot;

import java.time.LocalDate;
import java.util.Objects;

public class QuestUser extends AbstractDomainAggregateRoot {
    @Getter
    @Setter
    private Long id;

    private Long userId;
    private Long questId;
    private QuestStatus status;
    private Double progress;
    private LocalDate endDate;
    private Long CollaborativeSessionId;

    public QuestUser(Long id, Long userId, Long questId, Long CollaborativeSessionId) {
        this.id = id;
        this.userId = Objects.requireNonNull(userId, "userId is required");
        this.questId = Objects.requireNonNull(questId, "questId is required");
        this.status = QuestStatus.IN_PROGRESS;
        this.progress = 0.0;
        this.endDate = null;
        this.CollaborativeSessionId = CollaborativeSessionId;
    }

    public QuestUser(Long id, Long userId, Long questId, QuestStatus status, Double progress, LocalDate endDate, Long CollaborativeSessionId) {
        this.id = id;
        this.userId = Objects.requireNonNull(userId, "userId is required");
        this.questId = Objects.requireNonNull(questId, "questId is required");
        this.status = Objects.requireNonNull(status, "status is required");
        this.progress = Objects.requireNonNull(progress, "progress is required");
        this.endDate = endDate;
        this.CollaborativeSessionId = CollaborativeSessionId;
    }

    public QuestUser(Long userId, Long questId, QuestStatus status, Double progress, LocalDate endDate, Long CollaborativeSessionId){
        this(null, userId, questId, status, progress, endDate, CollaborativeSessionId);
    }

    public QuestUser(Long userId, Long questId, Long CollaborativeSessionId){
        this(null, userId, questId, CollaborativeSessionId);
    }

    public void onCreated(){
        registerEvent(QuestUserCreatedEvent.from(this));
    }

    public void updateProgress(Double progress) {
        if (progress == null || progress < 0 || progress > 100) {
            throw new IllegalArgumentException(
                    "Progress must be between 0 and 100"
            );
        }

        this.progress = progress;

        if (progress >= 100.0) {
            readyToComplete();
        } else if (this.status != QuestStatus.COMPLETED) {
            this.status = QuestStatus.IN_PROGRESS;
            this.endDate = null;
        }
    }

    private void readyToComplete() {
        this.progress = 100.0;
        this.status = QuestStatus.READY_TO_COMPLETE;
        this.endDate = null;
    }

    public void complete() {
        if (this.status != QuestStatus.READY_TO_COMPLETE) {
            throw new IllegalStateException("Quest status must be READY_TO_COMPLETE");
        }

        this.progress = 100.0;
        this.status = QuestStatus.COMPLETED;
        this.endDate = LocalDate.now();
    }

    public Long getUserId() {
        return userId;
    }

    public Long getQuestId() {
        return questId;
    }

    public QuestStatus getStatus() {
        return status;
    }

    public Double getProgress() {
        return progress;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Long getCollaborativeSessionId() {
        return CollaborativeSessionId;
    }
}
