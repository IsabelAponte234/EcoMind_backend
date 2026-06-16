package pe.greenminds.ecomind_backend.ranking.domain.model.aggregates;

import lombok.Getter;
import lombok.Setter;
import pe.greenminds.ecomind_backend.ranking.domain.model.commands.UpdateRankingCommand;
import pe.greenminds.ecomind_backend.ranking.domain.model.valueobjects.RankingType;
import pe.greenminds.ecomind_backend.shared.domain.model.aggregates.AbstractDomainAggregateRoot;

import java.util.Date;

@Getter
public class Ranking extends AbstractDomainAggregateRoot<Ranking> {

    @Setter
    private Long id;
    private String name;
    private RankingType type;
    private Date startDate;
    private Date endDate;
    private boolean status;

    public Ranking() {}

    public Ranking(String name, RankingType type, Date startDate, Date endDate, boolean status) {
        this.name = name;
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }

    public Ranking(String name, RankingType type) {
        this(name, type, new Date(), null, true);
    }

    public void updateInformation(String name, RankingType type, Date startDate, Date endDate, boolean status) {
        this.name = name;
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }

    public Ranking updateRanking(UpdateRankingCommand command) {
        this.name = command.name();
        this.type = RankingType.valueOf(command.type().toUpperCase());
        this.status = command.status();
        return this;
    }
}
