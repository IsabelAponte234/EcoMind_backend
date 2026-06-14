package pe.greenminds.ecomind_backend.monetization.application.internal.commandservices;

import org.springframework.stereotype.Service;
import pe.greenminds.ecomind_backend.monetization.application.commandservices.UserCosmeticCommandService;
import pe.greenminds.ecomind_backend.monetization.domain.model.aggregates.UserCosmetic;
import pe.greenminds.ecomind_backend.monetization.domain.model.commands.CreateUserCosmeticCommand;
import pe.greenminds.ecomind_backend.monetization.domain.model.commands.UpdateUserCosmeticCommand;
import pe.greenminds.ecomind_backend.monetization.domain.repositories.UserCosmeticRepository;
import pe.greenminds.ecomind_backend.shared.application.result.ApplicationError;
import pe.greenminds.ecomind_backend.shared.application.result.Result;

@Service
public class UserCosmeticCommandServiceImpl implements UserCosmeticCommandService {

    private final UserCosmeticRepository userCosmeticRepository;

    public UserCosmeticCommandServiceImpl(UserCosmeticRepository userCosmeticRepository) {
        this.userCosmeticRepository = userCosmeticRepository;
    }

    @Override
    public Result<UserCosmetic, ApplicationError> handle(CreateUserCosmeticCommand command) {
        try {
            var userCosmetic = new UserCosmetic(
                    command.userId(),
                    command.cosmeticId(),
                    command.acquiredAt(),
                    command.equipped()
            );

            return Result.success(userCosmeticRepository.save(userCosmetic));
        } catch (IllegalArgumentException e) {
            return Result.failure(
                    ApplicationError.validationError("UserCosmetic", e.getMessage())
            );
        } catch (Exception e) {
            return Result.failure(
                    ApplicationError.unexpected("UserCosmetic creation", e.getMessage())
            );
        }
    }

    @Override
    public Result<UserCosmetic, ApplicationError> handle(UpdateUserCosmeticCommand command) {
        var result = userCosmeticRepository.findById(command.userCosmeticId());
        if (result.isEmpty())
            return Result.failure(ApplicationError.notFound("UserCosmetic", command.userCosmeticId().toString()));
        try {
            var userCosmetic = new UserCosmetic(
                    command.userCosmeticId(),
                    command.userId(),
                    command.cosmeticId(),
                    command.acquiredAt(),
                    command.equipped()
            );
            return Result.success(userCosmeticRepository.save(userCosmetic));
        } catch (Exception e) {
            return Result.failure(
                    ApplicationError.unexpected("UserCosmetic update", e.getMessage())
            );
        }
    }
}
