package pe.greenminds.ecomind_backend.profile.application.commandservices;

import pe.greenminds.ecomind_backend.profile.domain.model.aggregates.User;
import pe.greenminds.ecomind_backend.profile.domain.model.commands.*;
import pe.greenminds.ecomind_backend.shared.application.result.ApplicationError;
import pe.greenminds.ecomind_backend.shared.application.result.Result;

public interface UserCommandService {
    Result<User, ApplicationError> handle(CreateUserCommand command);
    Result<User, ApplicationError> handle(UpdateUserCommand command);
    Result<User, ApplicationError> handle(UpdateUserCommitmentCommand command);
    Result<User, ApplicationError> handle(UpdateUserStatsCommand command);
    Result<User, ApplicationError> handle(DeleteUserCommand command);
}
