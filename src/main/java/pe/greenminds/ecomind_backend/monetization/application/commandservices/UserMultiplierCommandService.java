package pe.greenminds.ecomind_backend.monetization.application.commandservices;

import pe.greenminds.ecomind_backend.monetization.domain.model.aggregates.UserMultiplier;
import pe.greenminds.ecomind_backend.monetization.domain.model.commands.CreateUserMultiplierCommand;
import pe.greenminds.ecomind_backend.monetization.domain.model.commands.PurchaseUserMultiplierCommand;
import pe.greenminds.ecomind_backend.shared.application.result.ApplicationError;
import pe.greenminds.ecomind_backend.shared.application.result.Result;

public interface UserMultiplierCommandService {

    Result<UserMultiplier, ApplicationError> handle(CreateUserMultiplierCommand command);

    Result<UserMultiplier, ApplicationError> handle(PurchaseUserMultiplierCommand command);
}
