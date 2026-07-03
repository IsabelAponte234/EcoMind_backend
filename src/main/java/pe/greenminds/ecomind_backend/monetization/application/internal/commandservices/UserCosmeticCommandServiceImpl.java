package pe.greenminds.ecomind_backend.monetization.application.internal.commandservices;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import pe.greenminds.ecomind_backend.monetization.application.commandservices.UserCosmeticCommandService;
import pe.greenminds.ecomind_backend.monetization.application.outboundservices.external.ProfileMonetizationExternalService;
import pe.greenminds.ecomind_backend.monetization.domain.model.aggregates.GemMovement;
import pe.greenminds.ecomind_backend.monetization.domain.model.aggregates.UserCosmetic;
import pe.greenminds.ecomind_backend.monetization.domain.model.commands.CreateUserCosmeticCommand;
import pe.greenminds.ecomind_backend.monetization.domain.model.commands.PurchaseUserCosmeticCommand;
import pe.greenminds.ecomind_backend.monetization.domain.model.commands.UpdateUserCosmeticCommand;
import pe.greenminds.ecomind_backend.monetization.domain.model.valueobjects.MovementOrigin;
import pe.greenminds.ecomind_backend.monetization.domain.model.valueobjects.MovementType;
import pe.greenminds.ecomind_backend.monetization.domain.repositories.CosmeticRepository;
import pe.greenminds.ecomind_backend.monetization.domain.repositories.GemMovementRepository;
import pe.greenminds.ecomind_backend.monetization.domain.repositories.UserCosmeticRepository;
import pe.greenminds.ecomind_backend.shared.application.result.ApplicationError;
import pe.greenminds.ecomind_backend.shared.application.result.Result;

import java.time.LocalDateTime;

@Service
public class UserCosmeticCommandServiceImpl implements UserCosmeticCommandService {

    private final UserCosmeticRepository userCosmeticRepository;
    private final CosmeticRepository cosmeticRepository;
    private final GemMovementRepository gemMovementRepository;
    private final ProfileMonetizationExternalService profileMonetizationExternalService;

    public UserCosmeticCommandServiceImpl(
            UserCosmeticRepository userCosmeticRepository,
            CosmeticRepository cosmeticRepository,
            GemMovementRepository gemMovementRepository,
            ProfileMonetizationExternalService profileMonetizationExternalService
    ) {
        this.userCosmeticRepository = userCosmeticRepository;
        this.cosmeticRepository = cosmeticRepository;
        this.gemMovementRepository = gemMovementRepository;
        this.profileMonetizationExternalService = profileMonetizationExternalService;
    }

    @Transactional
    @Override
    public Result<UserCosmetic, ApplicationError> handle(CreateUserCosmeticCommand command) {
        if (!cosmeticRepository.existsById(command.cosmeticId())) {
            return Result.failure(
                    ApplicationError.notFound("Cosmetic", command.cosmeticId().toString())
            );
        }

        if (userCosmeticRepository.existsByUserIdAndCosmeticId(command.userId(), command.cosmeticId())) {
            return Result.failure(
                    ApplicationError.conflict(
                            "UserCosmetic",
                            "The cosmetic is already owned by this user"
                    )
            );
        }

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

    @Transactional
    @Override
    public Result<UserCosmetic, ApplicationError> handle(PurchaseUserCosmeticCommand command) {
        var cosmetic = cosmeticRepository.findById(command.cosmeticId());
        if (cosmetic.isEmpty()) {
            return Result.failure(
                    ApplicationError.notFound("Cosmetic", command.cosmeticId().toString())
            );
        }

        if (userCosmeticRepository.existsByUserIdAndCosmeticId(command.userId(), command.cosmeticId())) {
            return Result.failure(
                    ApplicationError.conflict(
                            "UserCosmetic",
                            "The cosmetic is already owned by this user"
                    )
            );
        }

        var spendResult = profileMonetizationExternalService.spendGems(
                command.userId(),
                cosmetic.get().getPrice()
        );
        if (spendResult instanceof Result.Failure<Void, ApplicationError> failure) {
            return Result.failure(failure.error());
        }

        var savedUserCosmetic = userCosmeticRepository.save(new UserCosmetic(
                command.userId(),
                command.cosmeticId(),
                LocalDateTime.now(),
                false
        ));

        gemMovementRepository.save(new GemMovement(
                command.userId(),
                MovementType.SPEND,
                -cosmetic.get().getPrice(),
                MovementOrigin.COSMETIC,
                command.cosmeticId()
        ));

        return Result.success(savedUserCosmetic);
    }

    @Transactional
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
