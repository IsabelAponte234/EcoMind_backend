package pe.greenminds.ecomind_backend.community.application.commandservices;

import pe.greenminds.ecomind_backend.community.domain.model.aggregates.Event;
import pe.greenminds.ecomind_backend.community.domain.model.commands.CreateEventCommand;
import pe.greenminds.ecomind_backend.community.domain.model.commands.DeleteEventCommand;
import pe.greenminds.ecomind_backend.shared.application.result.ApplicationError;
import pe.greenminds.ecomind_backend.shared.application.result.Result;

public interface EventCommandService {
    Result<Event, ApplicationError> handle(CreateEventCommand command);

    Result<Void, ApplicationError> handle(DeleteEventCommand command);
}