package pe.greenminds.ecomind_backend.ranking.infrastructure.persistence.jpa.entities;

import jakarta.persistence.*;
import pe.greenminds.ecomind_backend.shared.infrastructure.persistence.jpa.AuditableAbstractPersistenceEntity;

@Entity
@Table(name = "user_rankings")
public class UserRankingEntity extends AuditableAbstractPersistenceEntity {

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "ranking_id", nullable = false)
    private Long rankingId;

    @Column(name = "rank_position", nullable = false)
    private int rankPosition;

    @Column(name = "score", nullable = false)
    private int score;

    protected UserRankingEntity() {}

    public UserRankingEntity(Long userId, Long rankingId, int rankPosition, int score) {
        this.userId = userId;
        this.rankingId = rankingId;
        this.rankPosition = rankPosition;
        this.score = score;
    }

    public Long getUserId() { return userId; }
    public Long getRankingId() { return rankingId; }
    public int getRankPosition() { return rankPosition; }
    public int getScore() { return score; }

    public void updateInformation(Long userId, Long rankingId, int rankPosition, int score) {
        this.userId = userId;
        this.rankingId = rankingId;
        this.rankPosition = rankPosition;
        this.score = score;
    }
}
