package pe.greenminds.ecomind_backend.community.application.internal.commandservices;

import org.springframework.stereotype.Service;
import pe.greenminds.ecomind_backend.community.application.commandservices.CommunityCommandService;
import pe.greenminds.ecomind_backend.community.domain.model.aggregates.Community;
import pe.greenminds.ecomind_backend.community.domain.model.commands.CreateCommunityCommand;
import pe.greenminds.ecomind_backend.community.domain.repositories.CommunityRepository;
import pe.greenminds.ecomind_backend.shared.application.result.ApplicationError;
import pe.greenminds.ecomind_backend.shared.application.result.Result;

@Service
public class CommunityCommandServiceImpl implements CommunityCommandService {

    private final CommunityRepository communityRepository;

    public CommunityCommandServiceImpl(CommunityRepository communityRepository) {
        this.communityRepository = communityRepository;
    }

    @Override
    public Result<Community, ApplicationError> handle(CreateCommunityCommand command){
        try{
            var community = new Community(
                    command.name(),
                    command.location()
            );
            return Result.success(communityRepository.save(community));
        } catch (IllegalArgumentException | NullPointerException e){
            return Result.failure(
                    ApplicationError.validationError("Community", e.getMessage())
            );
        } catch (Exception e){
            return Result.failure(
                    ApplicationError.unexpected("Community creation", e.getMessage())
            );
        }
    }
}
