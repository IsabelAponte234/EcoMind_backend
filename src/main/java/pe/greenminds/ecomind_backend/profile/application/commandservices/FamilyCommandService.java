package pe.greenminds.ecomind_backend.profile.application.commandservices;

import pe.greenminds.ecomind_backend.profile.domain.model.aggregates.Family;
import pe.greenminds.ecomind_backend.profile.domain.model.commands.CreateFamilyCommand;
import pe.greenminds.ecomind_backend.profile.domain.model.commands.DeleteFamilyCommand;
import pe.greenminds.ecomind_backend.profile.domain.model.commands.UpdateFamilyCommand;
import pe.greenminds.ecomind_backend.profile.domain.model.commands.UpdateFamilyCommitmentCommand;
import pe.greenminds.ecomind_backend.shared.application.result.ApplicationError;
import pe.greenminds.ecomind_backend.shared.application.result.Result;

public interface FamilyCommandService {
    Result<Family, ApplicationError> handle(CreateFamilyCommand command);
    Result<Family, ApplicationError> handle(UpdateFamilyCommand command);
    Result<Family, ApplicationError> handle(UpdateFamilyCommitmentCommand command);
    Result<Family, ApplicationError> handle(DeleteFamilyCommand command);
}
