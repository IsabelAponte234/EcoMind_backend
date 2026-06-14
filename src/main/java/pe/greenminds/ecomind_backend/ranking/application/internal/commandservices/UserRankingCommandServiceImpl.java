package pe.greenminds.ecomind_backend.ranking.application.internal.commandservices;

import org.springframework.stereotype.Service;
import pe.greenminds.ecomind_backend.ranking.domain.model.commands.CreateUserRankingCommand;
import pe.greenminds.ecomind_backend.ranking.domain.model.commands.DeleteUserRankingCommand;
import pe.greenminds.ecomind_backend.ranking.domain.model.commands.UpdateUserRankingCommand;
import pe.greenminds.ecomind_backend.ranking.domain.services.UserRankingCommandService;
import pe.greenminds.ecomind_backend.ranking.infrastructure.persistence.jpa.entities.UserRankingEntity;
import pe.greenminds.ecomind_backend.ranking.infrastructure.persistence.jpa.repositories.UserRankingRepository;

import java.util.Optional;

@Service
public class UserRankingCommandServiceImpl implements UserRankingCommandService {

    private final UserRankingRepository userRankingRepository;

    public UserRankingCommandServiceImpl(UserRankingRepository userRankingRepository) {
        this.userRankingRepository = userRankingRepository;
    }

    @Override
    public Long handle(CreateUserRankingCommand command) {
        var userRanking = new UserRankingEntity(
                command.userId(),
                command.rankingId(),
                command.rankPosition(),
                command.score()
        );
        return userRankingRepository.save(userRanking).getId();
    }

    @Override
    public Optional<UserRankingEntity> handle(UpdateUserRankingCommand command) {
        var result = userRankingRepository.findById(command.userRankingId());
        if (result.isEmpty()) {
            throw new IllegalArgumentException("UserRanking with id " + command.userRankingId()
                    + " does not exist");
        }
        var userRanking = result.get();
        userRanking.updateInformation(
                command.userId(),
                command.rankingId(),
                command.rankPosition(),
                command.score()
        );
        return Optional.of(userRankingRepository.save(userRanking));
    }

    @Override
    public void handle(DeleteUserRankingCommand command) {
        if (!userRankingRepository.existsById(command.userRankingId())) {
            throw new IllegalArgumentException("UserRanking with id " + command.userRankingId()
                    + " does not exist");
        }
        userRankingRepository.deleteById(command.userRankingId());
    }
}
