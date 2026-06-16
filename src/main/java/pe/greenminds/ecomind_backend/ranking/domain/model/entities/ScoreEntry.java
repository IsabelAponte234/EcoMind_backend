package pe.greenminds.ecomind_backend.ranking.domain.model.entities;

import lombok.Getter;
import lombok.Setter;
import pe.greenminds.ecomind_backend.shared.domain.model.aggregates.AbstractDomainAggregateRoot;

import java.util.Date;

@Getter
public class ScoreEntry extends AbstractDomainAggregateRoot<ScoreEntry> {

    @Setter
    private Long id;
    private Long userId;
    private int score;
    private String entryType;
    private String description;
    private Date entryDate;

    public ScoreEntry() {}

    public ScoreEntry(Long userId, int score, String entryType, String description) {
        this.userId = userId;
        this.score = score;
        this.entryType = entryType;
        this.description = description;
        this.entryDate = new Date();
    }

    public ScoreEntry(Long userId, int score, String entryType, String description, Date entryDate) {
        this.userId = userId;
        this.score = score;
        this.entryType = entryType;
        this.description = description;
        this.entryDate = entryDate;
    }
}
