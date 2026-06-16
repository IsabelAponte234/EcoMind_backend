package pe.greenminds.ecomind_backend.ranking.application.internal.commandservices;

import org.springframework.stereotype.Service;
import pe.greenminds.ecomind_backend.ranking.domain.model.commands.CreateRankingCommand;
import pe.greenminds.ecomind_backend.ranking.domain.model.commands.DeleteRankingCommand;
import pe.greenminds.ecomind_backend.ranking.domain.model.commands.UpdateRankingCommand;
import pe.greenminds.ecomind_backend.ranking.domain.model.valueobjects.RankingType;
import pe.greenminds.ecomind_backend.ranking.domain.services.RankingCommandService;
import pe.greenminds.ecomind_backend.ranking.infrastructure.persistence.jpa.entities.RankingEntity;
import pe.greenminds.ecomind_backend.ranking.infrastructure.persistence.jpa.repositories.RankingRepository;

import java.util.Date;
import java.util.Optional;

@Service
public class RankingCommandServiceImpl implements RankingCommandService {

    private final RankingRepository rankingRepository;

    public RankingCommandServiceImpl(RankingRepository rankingRepository) {
        this.rankingRepository = rankingRepository;
    }

    @Override
    public Long handle(CreateRankingCommand command) {
        if (rankingRepository.existsByName(command.name())) {
            throw new IllegalArgumentException("Ranking with name " + command.name()
                    + " already exists");
        }
        var rankingType = RankingType.valueOf(command.type().toUpperCase());
        var ranking = new RankingEntity(command.name(), rankingType, new Date(), null, true);
        return rankingRepository.save(ranking).getId();
    }

    @Override
    public Optional<RankingEntity> handle(UpdateRankingCommand command) {
        var result = rankingRepository.findById(command.rankingId());
        if (result.isEmpty()) {
            throw new IllegalArgumentException("Ranking with id " + command.rankingId()
                    + " does not exist");
        }
        var ranking = result.get();
        ranking.updateRanking(command);
        return Optional.of(rankingRepository.save(ranking));
    }

    @Override
    public void handle(DeleteRankingCommand command) {
        if (!rankingRepository.existsById(command.rankingId())) {
            throw new IllegalArgumentException("Ranking with id " + command.rankingId()
                    + " does not exist");
        }
        rankingRepository.deleteById(command.rankingId());
    }
}
