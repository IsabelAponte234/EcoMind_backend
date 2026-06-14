package pe.greenminds.ecomind_backend.ranking.application.internal.queryservices;

import org.springframework.stereotype.Service;
import pe.greenminds.ecomind_backend.ranking.domain.model.queries.GetAllScoreEntriesQuery;
import pe.greenminds.ecomind_backend.ranking.domain.model.queries.GetScoreEntriesByUserIdQuery;
import pe.greenminds.ecomind_backend.ranking.domain.model.queries.GetScoreEntryByIdQuery;
import pe.greenminds.ecomind_backend.ranking.domain.services.ScoreEntryQueryService;
import pe.greenminds.ecomind_backend.ranking.infrastructure.persistence.jpa.entities.ScoreEntryEntity;
import pe.greenminds.ecomind_backend.ranking.infrastructure.persistence.jpa.repositories.ScoreEntryRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ScoreEntryQueryServiceImpl implements ScoreEntryQueryService {

    private final ScoreEntryRepository scoreEntryRepository;

    public ScoreEntryQueryServiceImpl(ScoreEntryRepository scoreEntryRepository) {
        this.scoreEntryRepository = scoreEntryRepository;
    }

    @Override
    public List<ScoreEntryEntity> handle(GetAllScoreEntriesQuery query) {
        return scoreEntryRepository.findAll();
    }

    @Override
    public Optional<ScoreEntryEntity> handle(GetScoreEntryByIdQuery query) {
        return scoreEntryRepository.findById(query.scoreEntryId());
    }

    @Override
    public List<ScoreEntryEntity> handle(GetScoreEntriesByUserIdQuery query) {
        return scoreEntryRepository.findByUserId(query.userId());
    }
}
