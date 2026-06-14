package pe.greenminds.ecomind_backend.ranking.domain.services;

import pe.greenminds.ecomind_backend.ranking.domain.model.queries.GetAllScoreEntriesQuery;
import pe.greenminds.ecomind_backend.ranking.domain.model.queries.GetScoreEntriesByUserIdQuery;
import pe.greenminds.ecomind_backend.ranking.domain.model.queries.GetScoreEntryByIdQuery;
import pe.greenminds.ecomind_backend.ranking.infrastructure.persistence.jpa.entities.ScoreEntryEntity;

import java.util.List;
import java.util.Optional;

public interface ScoreEntryQueryService {
    List<ScoreEntryEntity> handle(GetAllScoreEntriesQuery query);
    Optional<ScoreEntryEntity> handle(GetScoreEntryByIdQuery query);
    List<ScoreEntryEntity> handle(GetScoreEntriesByUserIdQuery query);
}
