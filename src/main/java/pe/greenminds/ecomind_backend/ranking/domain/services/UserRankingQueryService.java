package pe.greenminds.ecomind_backend.ranking.domain.services;

import pe.greenminds.ecomind_backend.ranking.domain.model.queries.GetAllUserRankingsQuery;
import pe.greenminds.ecomind_backend.ranking.domain.model.queries.GetUserRankingByIdQuery;
import pe.greenminds.ecomind_backend.ranking.domain.model.queries.GetUserRankingsByRankingIdQuery;
import pe.greenminds.ecomind_backend.ranking.infrastructure.persistence.jpa.entities.UserRankingEntity;

import java.util.List;
import java.util.Optional;

public interface UserRankingQueryService {
    List<UserRankingEntity> handle(GetAllUserRankingsQuery query);
    Optional<UserRankingEntity> handle(GetUserRankingByIdQuery query);
    List<UserRankingEntity> handle(GetUserRankingsByRankingIdQuery query);
}
