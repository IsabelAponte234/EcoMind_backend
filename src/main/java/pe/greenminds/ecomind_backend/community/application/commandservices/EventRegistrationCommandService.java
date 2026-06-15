package pe.greenminds.ecomind_backend.community.application.commandservices;

import pe.greenminds.ecomind_backend.community.domain.model.aggregates.EventRegistration;
import pe.greenminds.ecomind_backend.community.domain.model.commands.CancelEventRegistrationCommand;
import pe.greenminds.ecomind_backend.community.domain.model.commands.CreateEventRegistrationCommand;
import pe.greenminds.ecomind_backend.shared.application.result.ApplicationError;
import pe.greenminds.ecomind_backend.shared.application.result.Result;

public interface EventRegistrationCommandService {
    Result<EventRegistration, ApplicationError> handle(CreateEventRegistrationCommand command);
    Result<EventRegistration, ApplicationError> handle(CancelEventRegistrationCommand command);
}