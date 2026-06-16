package pe.greenminds.ecomind_backend.ranking.application.internal.commandservices;

import org.springframework.stereotype.Service;
import pe.greenminds.ecomind_backend.ranking.domain.model.commands.CreateScoreEntryCommand;
import pe.greenminds.ecomind_backend.ranking.domain.model.commands.DeleteScoreEntryCommand;
import pe.greenminds.ecomind_backend.ranking.domain.services.ScoreEntryCommandService;
import pe.greenminds.ecomind_backend.ranking.infrastructure.persistence.jpa.entities.ScoreEntryEntity;
import pe.greenminds.ecomind_backend.ranking.infrastructure.persistence.jpa.repositories.ScoreEntryRepository;

@Service
public class ScoreEntryCommandServiceImpl implements ScoreEntryCommandService {

    private final ScoreEntryRepository scoreEntryRepository;

    public ScoreEntryCommandServiceImpl(ScoreEntryRepository scoreEntryRepository) {
        this.scoreEntryRepository = scoreEntryRepository;
    }

    @Override
    public Long handle(CreateScoreEntryCommand command) {
        var scoreEntry = new ScoreEntryEntity(
                command.userId(),
                command.score(),
                command.entryType(),
                command.description()
        );
        return scoreEntryRepository.save(scoreEntry).getId();
    }

    @Override
    public void handle(DeleteScoreEntryCommand command) {
        if (!scoreEntryRepository.existsById(command.scoreEntryId())) {
            throw new IllegalArgumentException("ScoreEntry with id " + command.scoreEntryId()
                    + " does not exist");
        }
        scoreEntryRepository.deleteById(command.scoreEntryId());
    }
}
