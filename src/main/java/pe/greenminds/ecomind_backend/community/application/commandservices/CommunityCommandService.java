package pe.greenminds.ecomind_backend.community.application.commandservices;

import pe.greenminds.ecomind_backend.community.domain.model.aggregates.Community;
import pe.greenminds.ecomind_backend.community.domain.model.commands.CreateCommunityCommand;
import pe.greenminds.ecomind_backend.shared.application.result.ApplicationError;
import pe.greenminds.ecomind_backend.shared.application.result.Result;

public interface CommunityCommandService {
    Result<Community, ApplicationError> handle(CreateCommunityCommand command);
}