package pe.greenminds.ecomind_backend.ranking.application.internal.queryservices;

import org.springframework.stereotype.Service;
import pe.greenminds.ecomind_backend.ranking.domain.model.queries.GetAllUserRankingsQuery;
import pe.greenminds.ecomind_backend.ranking.domain.model.queries.GetUserRankingByIdQuery;
import pe.greenminds.ecomind_backend.ranking.domain.model.queries.GetUserRankingsByRankingIdQuery;
import pe.greenminds.ecomind_backend.ranking.domain.services.UserRankingQueryService;
import pe.greenminds.ecomind_backend.ranking.infrastructure.persistence.jpa.entities.UserRankingEntity;
import pe.greenminds.ecomind_backend.ranking.infrastructure.persistence.jpa.repositories.UserRankingRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserRankingQueryServiceImpl implements UserRankingQueryService {

    private final UserRankingRepository userRankingRepository;

    public UserRankingQueryServiceImpl(UserRankingRepository userRankingRepository) {
        this.userRankingRepository = userRankingRepository;
    }

    @Override
    public List<UserRankingEntity> handle(GetAllUserRankingsQuery query) {
        return userRankingRepository.findAll();
    }

    @Override
    public Optional<UserRankingEntity> handle(GetUserRankingByIdQuery query) {
        return userRankingRepository.findById(query.userRankingId());
    }

    @Override
    public List<UserRankingEntity> handle(GetUserRankingsByRankingIdQuery query) {
        return userRankingRepository.findByRankingId(query.rankingId());
    }
}
