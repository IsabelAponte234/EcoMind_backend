package pe.greenminds.ecomind_backend.quests.application.commandservices;

import pe.greenminds.ecomind_backend.quests.domain.model.aggregates.Activity;
import pe.greenminds.ecomind_backend.quests.domain.model.commands.CreateActivityCommand;
import pe.greenminds.ecomind_backend.quests.domain.model.commands.DeleteActivityCommand;
import pe.greenminds.ecomind_backend.quests.domain.model.commands.UpdateActivityCommand;
import pe.greenminds.ecomind_backend.shared.application.result.ApplicationError;
import pe.greenminds.ecomind_backend.shared.application.result.Result;

public interface ActivityCommandService {
    Result<Activity, ApplicationError> handle(CreateActivityCommand command);

    Result<Activity, ApplicationError> handle(DeleteActivityCommand command);

    Result<Activity, ApplicationError> handle(UpdateActivityCommand command);
}
