package pe.greenminds.ecomind_backend.community.application.internal.commandservices;

import org.springframework.stereotype.Service;
import pe.greenminds.ecomind_backend.community.application.commandservices.PostCommandService;
import pe.greenminds.ecomind_backend.community.domain.model.aggregates.Post;
import pe.greenminds.ecomind_backend.community.domain.model.commands.CreatePostCommand;
import pe.greenminds.ecomind_backend.community.domain.model.commands.DeletePostCommand;
import pe.greenminds.ecomind_backend.community.domain.repositories.PostRepository;
import pe.greenminds.ecomind_backend.shared.application.result.ApplicationError;
import pe.greenminds.ecomind_backend.shared.application.result.Result;

@Service
public class PostCommandServiceImpl implements PostCommandService {

    private final PostRepository postRepository;

    public PostCommandServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public Result<Post, ApplicationError> handle(CreatePostCommand command) {
        try{
            var post = new Post(
                    command.communityId(),
                    command.userId(),
                    command.content(),
                    command.points(),
                    command.imageUrl()
            );
            return Result.success(postRepository.save(post));
        } catch (IllegalArgumentException | NullPointerException e){
            return Result.failure(
                    ApplicationError.validationError("Post", e.getMessage())
            );
        } catch (Exception e){
            return Result.failure(
                    ApplicationError.unexpected("Post creation", e.getMessage())
            );
        }
    }

    @Override
    public Result<Void, ApplicationError> handle(DeletePostCommand command){
        try{
            if(!postRepository.existsById(command.id())){
                return Result.failure(
                        ApplicationError.notFound("Post", command.id().toString())
                );
            }
            postRepository.deleteById(command.id());
            return Result.success(null);
        } catch (Exception e){
            return Result.failure(
                    ApplicationError.unexpected("Post deletion", e.getMessage())
            );
        }
    }
}
