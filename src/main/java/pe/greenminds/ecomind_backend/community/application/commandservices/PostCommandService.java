package pe.greenminds.ecomind_backend.community.application.commandservices;

import pe.greenminds.ecomind_backend.community.domain.model.aggregates.Post;
import pe.greenminds.ecomind_backend.community.domain.model.commands.CreatePostCommand;
import pe.greenminds.ecomind_backend.community.domain.model.commands.DeletePostCommand;
import pe.greenminds.ecomind_backend.shared.application.result.ApplicationError;
import pe.greenminds.ecomind_backend.shared.application.result.Result;

public interface PostCommandService {
    Result<Post, ApplicationError> handle(CreatePostCommand command);

    Result<Void, ApplicationError> handle(DeletePostCommand command);
}
