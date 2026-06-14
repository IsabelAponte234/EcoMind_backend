package pe.greenminds.ecomind_backend.quests.application.commandservices;

import pe.greenminds.ecomind_backend.quests.domain.model.aggregates.QuestUser;
import pe.greenminds.ecomind_backend.quests.domain.model.commands.CompleteQuestUserCommand;
import pe.greenminds.ecomind_backend.quests.domain.model.commands.CreateQuestUserCommand;
import pe.greenminds.ecomind_backend.quests.domain.model.commands.DeleteQuestUserCommand;
import pe.greenminds.ecomind_backend.shared.application.result.ApplicationError;
import pe.greenminds.ecomind_backend.shared.application.result.Result;

public interface QuestUserCommandService {
    Result<QuestUser, ApplicationError> handle(CreateQuestUserCommand command);

    Result<QuestUser, ApplicationError> handle(DeleteQuestUserCommand command);

    Result<QuestUser, ApplicationError> handle(CompleteQuestUserCommand command);
}
