package pe.greenminds.ecomind_backend.monetization.application.commandservices;

import pe.greenminds.ecomind_backend.monetization.domain.model.aggregates.GemPackage;
import pe.greenminds.ecomind_backend.monetization.domain.model.commands.CreateGemPackageCommand;
import pe.greenminds.ecomind_backend.shared.application.result.ApplicationError;
import pe.greenminds.ecomind_backend.shared.application.result.Result;

public interface GemPackageCommandService {

    Result<GemPackage, ApplicationError> handle(CreateGemPackageCommand command);
}
