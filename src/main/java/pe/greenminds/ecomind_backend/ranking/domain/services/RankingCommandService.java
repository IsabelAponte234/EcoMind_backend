package pe.greenminds.ecomind_backend.ranking.domain.services;

import pe.greenminds.ecomind_backend.ranking.domain.model.commands.CreateRankingCommand;
import pe.greenminds.ecomind_backend.ranking.domain.model.commands.DeleteRankingCommand;
import pe.greenminds.ecomind_backend.ranking.domain.model.commands.UpdateRankingCommand;
import pe.greenminds.ecomind_backend.ranking.infrastructure.persistence.jpa.entities.RankingEntity;

import java.util.Optional;

public interface RankingCommandService {
    Long handle(CreateRankingCommand command);
    Optional<RankingEntity> handle(UpdateRankingCommand command);
    void handle(DeleteRankingCommand command);
}
