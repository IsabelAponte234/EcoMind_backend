package pe.greenminds.ecomind_backend.ranking.domain.services;

import pe.greenminds.ecomind_backend.ranking.domain.model.commands.CreateScoreEntryCommand;
import pe.greenminds.ecomind_backend.ranking.domain.model.commands.DeleteScoreEntryCommand;

public interface ScoreEntryCommandService {
    Long handle(CreateScoreEntryCommand command);
    void handle(DeleteScoreEntryCommand command);
}
