package pe.greenminds.ecomind_backend.monetization.application.internal.commandservices;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import pe.greenminds.ecomind_backend.monetization.application.commandservices.UserMultiplierCommandService;
import pe.greenminds.ecomind_backend.monetization.application.outboundservices.external.ProfileMonetizationExternalService;
import pe.greenminds.ecomind_backend.monetization.domain.model.aggregates.GemMovement;
import pe.greenminds.ecomind_backend.monetization.domain.model.aggregates.UserMultiplier;
import pe.greenminds.ecomind_backend.monetization.domain.model.commands.CreateUserMultiplierCommand;
import pe.greenminds.ecomind_backend.monetization.domain.model.commands.PurchaseUserMultiplierCommand;
import pe.greenminds.ecomind_backend.monetization.domain.model.valueobjects.MovementOrigin;
import pe.greenminds.ecomind_backend.monetization.domain.model.valueobjects.MovementType;
import pe.greenminds.ecomind_backend.monetization.domain.repositories.GemMovementRepository;
import pe.greenminds.ecomind_backend.monetization.domain.repositories.MultiplierRepository;
import pe.greenminds.ecomind_backend.monetization.domain.repositories.UserMultiplierRepository;
import pe.greenminds.ecomind_backend.shared.application.result.ApplicationError;
import pe.greenminds.ecomind_backend.shared.application.result.Result;

import java.time.LocalDateTime;

@Service
public class UserMultiplierCommandServiceImpl implements UserMultiplierCommandService {

    private final UserMultiplierRepository userMultiplierRepository;
    private final MultiplierRepository multiplierRepository;
    private final GemMovementRepository gemMovementRepository;
    private final ProfileMonetizationExternalService profileMonetizationExternalService;

    public UserMultiplierCommandServiceImpl(
            UserMultiplierRepository userMultiplierRepository,
            MultiplierRepository multiplierRepository,
            GemMovementRepository gemMovementRepository,
            ProfileMonetizationExternalService profileMonetizationExternalService
    ) {
        this.userMultiplierRepository = userMultiplierRepository;
        this.multiplierRepository = multiplierRepository;
        this.gemMovementRepository = gemMovementRepository;
        this.profileMonetizationExternalService = profileMonetizationExternalService;
    }

    @Transactional
    @Override
    public Result<UserMultiplier, ApplicationError> handle(CreateUserMultiplierCommand command) {
        if (!multiplierRepository.existsById(command.multiplierId())) {
            return Result.failure(
                    ApplicationError.notFound("Multiplier", command.multiplierId().toString())
            );
        }

        try {
            var userMultiplier = new UserMultiplier(
                    command.userId(),
                    command.multiplierId(),
                    command.startDate(),
                    command.endDate()
            );

            return Result.success(userMultiplierRepository.save(userMultiplier));
        } catch (IllegalArgumentException e) {
            return Result.failure(
                    ApplicationError.validationError("UserMultiplier", e.getMessage())
            );
        } catch (Exception e) {
            return Result.failure(
                    ApplicationError.unexpected("UserMultiplier creation", e.getMessage())
            );
        }
    }

    @Transactional
    @Override
    public Result<UserMultiplier, ApplicationError> handle(PurchaseUserMultiplierCommand command) {
        var multiplier = multiplierRepository.findById(command.multiplierId());
        if (multiplier.isEmpty()) {
            return Result.failure(
                    ApplicationError.notFound("Multiplier", command.multiplierId().toString())
            );
        }

        var spendResult = profileMonetizationExternalService.spendGems(
                command.userId(),
                multiplier.get().getGemCost()
        );
        if (spendResult instanceof Result.Failure<Void, ApplicationError> failure) {
            return Result.failure(failure.error());
        }

        var start = LocalDateTime.now();
        var end = start.plusMinutes(multiplier.get().getDurationMinutes());

        var savedUserMultiplier = userMultiplierRepository.save(new UserMultiplier(
                command.userId(),
                command.multiplierId(),
                start,
                end
        ));

        gemMovementRepository.save(new GemMovement(
                command.userId(),
                MovementType.SPEND,
                -multiplier.get().getGemCost(),
                MovementOrigin.MULTIPLIER,
                command.multiplierId()
        ));

        return Result.success(savedUserMultiplier);
    }
}
