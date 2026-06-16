package pe.greenminds.ecomind_backend.ranking.application.internal.queryservices;

import org.springframework.stereotype.Service;
import pe.greenminds.ecomind_backend.ranking.domain.model.queries.GetAllRankingsQuery;
import pe.greenminds.ecomind_backend.ranking.domain.model.queries.GetRankingByIdQuery;
import pe.greenminds.ecomind_backend.ranking.domain.services.RankingQueryService;
import pe.greenminds.ecomind_backend.ranking.infrastructure.persistence.jpa.entities.RankingEntity;
import pe.greenminds.ecomind_backend.ranking.infrastructure.persistence.jpa.repositories.RankingRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RankingQueryServiceImpl implements RankingQueryService {

    private final RankingRepository rankingRepository;

    public RankingQueryServiceImpl(RankingRepository rankingRepository) {
        this.rankingRepository = rankingRepository;
    }

    @Override
    public List<RankingEntity> handle(GetAllRankingsQuery query) {
        return rankingRepository.findAll();
    }

    @Override
    public Optional<RankingEntity> handle(GetRankingByIdQuery query) {
        return rankingRepository.findById(query.rankingId());
    }
}
