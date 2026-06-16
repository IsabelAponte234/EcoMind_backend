package pe.greenminds.ecomind_backend.ranking.domain.model.aggregates;

import lombok.Getter;
import lombok.Setter;
import pe.greenminds.ecomind_backend.shared.domain.model.aggregates.AbstractDomainAggregateRoot;

@Getter
public class UserRanking extends AbstractDomainAggregateRoot<UserRanking> {

    @Setter
    private Long id;
    private Long userId;
    private Long rankingId;
    private int rankPosition;
    private int score;

    public UserRanking() {}

    public UserRanking(Long userId, Long rankingId, int rankPosition, int score) {
        this.userId = userId;
        this.rankingId = rankingId;
        this.rankPosition = rankPosition;
        this.score = score;
    }

    public void updateInformation(Long userId, Long rankingId, int rankPosition, int score) {
        this.userId = userId;
        this.rankingId = rankingId;
        this.rankPosition = rankPosition;
        this.score = score;
    }
}
