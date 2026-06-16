package pe.greenminds.ecomind_backend.profile.application.commandservices;

import pe.greenminds.ecomind_backend.profile.domain.model.aggregates.FamilyUser;
import pe.greenminds.ecomind_backend.profile.domain.model.commands.CreateFamilyUserCommand;
import pe.greenminds.ecomind_backend.profile.domain.model.commands.DeleteFamilyUserCommand;
import pe.greenminds.ecomind_backend.profile.domain.model.commands.UpdateFamilyUserCommand;
import pe.greenminds.ecomind_backend.shared.application.result.ApplicationError;
import pe.greenminds.ecomind_backend.shared.application.result.Result;

public interface FamilyUserCommandService {
    Result<FamilyUser, ApplicationError> handle(CreateFamilyUserCommand command);
    Result<FamilyUser, ApplicationError> handle(UpdateFamilyUserCommand command);
    Result<FamilyUser, ApplicationError> handle(DeleteFamilyUserCommand command);
}
