package pe.greenminds.ecomind_backend.profile.application.commandservices;

import pe.greenminds.ecomind_backend.profile.domain.model.aggregates.FamilyInvitation;
import pe.greenminds.ecomind_backend.profile.domain.model.commands.*;
import pe.greenminds.ecomind_backend.shared.application.result.ApplicationError;
import pe.greenminds.ecomind_backend.shared.application.result.Result;

public interface FamilyInvitationCommandService {
    Result<FamilyInvitation, ApplicationError> handle(CreateFamilyInvitationCommand command);
    Result<FamilyInvitation, ApplicationError> handle(UpdateFamilyInvitationCommand command);
    Result<FamilyInvitation, ApplicationError> handle(AcceptFamilyInvitationCommand command);
    Result<FamilyInvitation, ApplicationError> handle(RejectFamilyInvitationCommand command);
    Result<FamilyInvitation, ApplicationError> handle(DeleteFamilyInvitationCommand command);
}
