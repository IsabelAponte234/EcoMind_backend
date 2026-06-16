package pe.greenminds.ecomind_backend.profile.application.commandservices;

import pe.greenminds.ecomind_backend.profile.domain.model.aggregates.Friend;
import pe.greenminds.ecomind_backend.profile.domain.model.commands.*;
import pe.greenminds.ecomind_backend.shared.application.result.ApplicationError;
import pe.greenminds.ecomind_backend.shared.application.result.Result;

public interface FriendCommandService {
    Result<Friend, ApplicationError> handle(CreateFriendCommand command);
    Result<Friend, ApplicationError> handle(UpdateFriendCommand command);
    Result<Friend, ApplicationError> handle(AcceptFriendCommand command);
    Result<Friend, ApplicationError> handle(RejectFriendCommand command);
    Result<Friend, ApplicationError> handle(DeleteFriendCommand command);
}
