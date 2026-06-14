package pe.greenminds.ecomind_backend.monetization.application.internal.commandservices;

import org.springframework.stereotype.Service;
import pe.greenminds.ecomind_backend.monetization.application.commandservices.GemMovementCommandService;
import pe.greenminds.ecomind_backend.monetization.domain.model.aggregates.GemMovement;
import pe.greenminds.ecomind_backend.monetization.domain.model.commands.CreateGemMovementCommand;
import pe.greenminds.ecomind_backend.monetization.domain.repositories.GemMovementRepository;
import pe.greenminds.ecomind_backend.shared.application.result.ApplicationError;
import pe.greenminds.ecomind_backend.shared.application.result.Result;

@Service
public class GemMovementCommandServiceImpl implements GemMovementCommandService {

    private final GemMovementRepository gemMovementRepository;

    public GemMovementCommandServiceImpl(GemMovementRepository gemMovementRepository) {
        this.gemMovementRepository = gemMovementRepository;
    }

    @Override
    public Result<GemMovement, ApplicationError> handle(CreateGemMovementCommand command) {
        try {
            var gemMovement = new GemMovement(
                    command.userId(),
                    command.type(),
                    command.amount(),
                    command.origin(),
                    command.originId()
            );

            return Result.success(gemMovementRepository.save(gemMovement));
        } catch (IllegalArgumentException e) {
            return Result.failure(
                    ApplicationError.validationError("GemMovement", e.getMessage())
            );
        } catch (Exception e) {
            return Result.failure(
                    ApplicationError.unexpected("GemMovement creation", e.getMessage())
            );
        }
    }
}
