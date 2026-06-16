package pe.greenminds.ecomind_backend.ranking.infrastructure.persistence.jpa.entities;

import jakarta.persistence.*;
import pe.greenminds.ecomind_backend.ranking.domain.model.commands.UpdateRankingCommand;
import pe.greenminds.ecomind_backend.ranking.domain.model.valueobjects.RankingType;
import pe.greenminds.ecomind_backend.shared.infrastructure.persistence.jpa.AuditableAbstractPersistenceEntity;

import java.util.Date;

@Entity
@Table(name = "rankings")
public class RankingEntity extends AuditableAbstractPersistenceEntity {

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", length = 20, nullable = false)
    private RankingType type;

    @Column(name = "start_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;

    @Column(name = "end_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    @Column(name = "status", nullable = false)
    private boolean status;

    protected RankingEntity() {}

    public RankingEntity(String name, RankingType type, Date startDate, Date endDate, boolean status) {
        this.name = name;
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }

    public String getName() { return name; }
    public RankingType getType() { return type; }
    public Date getStartDate() { return startDate; }
    public Date getEndDate() { return endDate; }
    public boolean isStatus() { return status; }

    public void updateRanking(UpdateRankingCommand command) {
        this.name = command.name();
        this.type = RankingType.valueOf(command.type().toUpperCase());
        this.status = command.status();
    }
}
