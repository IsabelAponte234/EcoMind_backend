package pe.greenminds.ecomind_backend.ranking.domain.services;

import pe.greenminds.ecomind_backend.ranking.domain.model.queries.GetAllRankingsQuery;
import pe.greenminds.ecomind_backend.ranking.domain.model.queries.GetRankingByIdQuery;
import pe.greenminds.ecomind_backend.ranking.infrastructure.persistence.jpa.entities.RankingEntity;

import java.util.List;
import java.util.Optional;

public interface RankingQueryService {
    List<RankingEntity> handle(GetAllRankingsQuery query);
    Optional<RankingEntity> handle(GetRankingByIdQuery query);
}
