package pe.greenminds.ecomind_backend.monetization.application.commandservices;

import pe.greenminds.ecomind_backend.monetization.domain.model.aggregates.UserCosmetic;
import pe.greenminds.ecomind_backend.monetization.domain.model.commands.CreateUserCosmeticCommand;
import pe.greenminds.ecomind_backend.monetization.domain.model.commands.UpdateUserCosmeticCommand;
import pe.greenminds.ecomind_backend.shared.application.result.ApplicationError;
import pe.greenminds.ecomind_backend.shared.application.result.Result;

public interface UserCosmeticCommandService {

    Result<UserCosmetic, ApplicationError> handle(CreateUserCosmeticCommand command);

    Result<UserCosmetic, ApplicationError> handle(UpdateUserCosmeticCommand command);
}
