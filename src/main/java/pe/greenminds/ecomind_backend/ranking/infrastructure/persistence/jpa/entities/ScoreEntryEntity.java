package pe.greenminds.ecomind_backend.ranking.infrastructure.persistence.jpa.entities;

import jakarta.persistence.*;
import pe.greenminds.ecomind_backend.shared.infrastructure.persistence.jpa.AuditableAbstractPersistenceEntity;

import java.util.Date;

@Entity
@Table(name = "score_entries")
public class ScoreEntryEntity extends AuditableAbstractPersistenceEntity {

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "score", nullable = false)
    private int score;

    @Column(name = "entry_type", length = 50, nullable = false)
    private String entryType;

    @Column(name = "description", length = 255)
    private String description;

    @Column(name = "entry_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date entryDate;

    protected ScoreEntryEntity() {}

    public ScoreEntryEntity(Long userId, int score, String entryType, String description) {
        this.userId = userId;
        this.score = score;
        this.entryType = entryType;
        this.description = description;
        this.entryDate = new Date();
    }

    public ScoreEntryEntity(Long userId, int score, String entryType, String description, Date entryDate) {
        this.userId = userId;
        this.score = score;
        this.entryType = entryType;
        this.description = description;
        this.entryDate = entryDate;
    }

    public Long getUserId() { return userId; }
    public int getScore() { return score; }
    public String getEntryType() { return entryType; }
    public String getDescription() { return description; }
    public Date getEntryDate() { return entryDate; }
}
