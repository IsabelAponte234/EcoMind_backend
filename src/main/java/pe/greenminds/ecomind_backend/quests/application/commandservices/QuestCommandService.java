package pe.greenminds.ecomind_backend.quests.application.commandservices;

import pe.greenminds.ecomind_backend.quests.domain.model.aggregates.Quest;
import pe.greenminds.ecomind_backend.quests.domain.model.commands.CreateQuestCommand;
import pe.greenminds.ecomind_backend.quests.domain.model.commands.DeleteQuestCommand;
import pe.greenminds.ecomind_backend.quests.domain.model.commands.UpdateQuestCommand;
import pe.greenminds.ecomind_backend.shared.application.result.ApplicationError;
import pe.greenminds.ecomind_backend.shared.application.result.Result;

public interface QuestCommandService {

    Result<Quest, ApplicationError> handle(CreateQuestCommand command);

    Result<Quest, ApplicationError> handle(DeleteQuestCommand command);

    Result<Quest, ApplicationError> handle(UpdateQuestCommand command);
}
