package pe.greenminds.ecomind_backend.monetization.application.internal.commandservices;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import pe.greenminds.ecomind_backend.monetization.application.commandservices.GemPurchaseCommandService;
import pe.greenminds.ecomind_backend.monetization.domain.model.aggregates.GemPurchase;
import pe.greenminds.ecomind_backend.monetization.domain.model.commands.ApproveGemPurchaseCommand;
import pe.greenminds.ecomind_backend.monetization.domain.model.commands.CreateGemPurchaseCheckoutCommand;
import pe.greenminds.ecomind_backend.monetization.domain.model.commands.CreateGemPurchaseCommand;
import pe.greenminds.ecomind_backend.monetization.application.outboundservices.external.ProfileMonetizationExternalService;
import pe.greenminds.ecomind_backend.monetization.domain.model.aggregates.GemMovement;
import pe.greenminds.ecomind_backend.monetization.domain.model.commands.RejectGemPurchaseCommand;
import pe.greenminds.ecomind_backend.monetization.domain.model.valueobjects.MovementOrigin;
import pe.greenminds.ecomind_backend.monetization.domain.model.valueobjects.MovementType;
import pe.greenminds.ecomind_backend.monetization.domain.model.valueobjects.PaymentStatus;
import pe.greenminds.ecomind_backend.monetization.domain.repositories.GemMovementRepository;
import pe.greenminds.ecomind_backend.monetization.domain.repositories.GemPackageRepository;
import pe.greenminds.ecomind_backend.monetization.domain.repositories.GemPurchaseRepository;
import pe.greenminds.ecomind_backend.shared.application.result.ApplicationError;
import pe.greenminds.ecomind_backend.shared.application.result.Result;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class GemPurchaseCommandServiceImpl implements GemPurchaseCommandService {

    private final GemPurchaseRepository gemPurchaseRepository;
    private final GemPackageRepository gemPackageRepository;
    private final GemMovementRepository gemMovementRepository;
    private final ProfileMonetizationExternalService profileMonetizationExternalService;

    public GemPurchaseCommandServiceImpl(
            GemPurchaseRepository gemPurchaseRepository,
            GemPackageRepository gemPackageRepository,
            GemMovementRepository gemMovementRepository,
            ProfileMonetizationExternalService profileMonetizationExternalService
    ) {
        this.gemPurchaseRepository = gemPurchaseRepository;
        this.gemPackageRepository = gemPackageRepository;
        this.gemMovementRepository = gemMovementRepository;
        this.profileMonetizationExternalService = profileMonetizationExternalService;
    }

    @Transactional
    @Override
    public Result<GemPurchase, ApplicationError> handle(CreateGemPurchaseCommand command) {
        if (!gemPackageRepository.existsById(command.packageId())) {
            return Result.failure(
                    ApplicationError.notFound("GemPackage", command.packageId().toString())
            );
        }

        try {
            var gemPurchase = new GemPurchase(
                    command.userId(),
                    command.packageId(),
                    command.purchaseDate(),
                    command.amountPaid(),
                    command.paymentStatus(),
                    command.paymentReference()
            );

            return Result.success(gemPurchaseRepository.save(gemPurchase));
        } catch (IllegalArgumentException e) {
            return Result.failure(
                    ApplicationError.validationError("GemPurchase", e.getMessage())
            );
        } catch (Exception e) {
            return Result.failure(
                    ApplicationError.unexpected("GemPurchase creation", e.getMessage())
            );
        }
    }

    @Transactional
    @Override
    public Result<GemPurchase, ApplicationError> handle(CreateGemPurchaseCheckoutCommand command) {
        var gemPackage = gemPackageRepository.findById(command.packageId());
        if (gemPackage.isEmpty()) {
            return Result.failure(
                    ApplicationError.notFound("GemPackage", command.packageId().toString())
            );
        }

        try {
            var gemPurchase = new GemPurchase(
                    command.userId(),
                    command.packageId(),
                    LocalDateTime.now(),
                    gemPackage.get().getRealPrice(),
                    PaymentStatus.PENDING,
                    "PENDING-" + UUID.randomUUID()
            );

            return Result.success(gemPurchaseRepository.save(gemPurchase));
        } catch (Exception e) {
            return Result.failure(
                    ApplicationError.unexpected("GemPurchase checkout", e.getMessage())
            );
        }
    }

    @Transactional
    @Override
    public Result<GemPurchase, ApplicationError> handle(ApproveGemPurchaseCommand command) {
        var gemPurchase = gemPurchaseRepository.findById(command.gemPurchaseId());
        if (gemPurchase.isEmpty()) {
            return Result.failure(
                    ApplicationError.notFound("GemPurchase", command.gemPurchaseId().toString())
            );
        }

        var gemPackage = gemPackageRepository.findById(gemPurchase.get().getPackageId());
        if (gemPackage.isEmpty()) {
            return Result.failure(
                    ApplicationError.notFound("GemPackage", gemPurchase.get().getPackageId().toString())
            );
        }

        try {
            gemPurchase.get().approve();
        } catch (IllegalStateException e) {
            return Result.failure(
                    ApplicationError.businessRuleViolation("GemPurchase approval", e.getMessage())
            );
        }

        var creditResult = profileMonetizationExternalService.creditGems(
                gemPurchase.get().getUserId(),
                gemPackage.get().getGemAmount()
        );
        if (creditResult instanceof Result.Failure<Void, ApplicationError> failure) {
            return Result.failure(failure.error());
        }

        var savedGemPurchase = gemPurchaseRepository.save(gemPurchase.get());

        gemMovementRepository.save(new GemMovement(
                savedGemPurchase.getUserId(),
                MovementType.PURCHASE,
                gemPackage.get().getGemAmount(),
                MovementOrigin.GEM_PACKAGE,
                savedGemPurchase.getPackageId()
        ));

        return Result.success(savedGemPurchase);
    }

    @Transactional
    @Override
    public Result<GemPurchase, ApplicationError> handle(RejectGemPurchaseCommand command) {
        var gemPurchase = gemPurchaseRepository.findById(command.gemPurchaseId());
        if (gemPurchase.isEmpty()) {
            return Result.failure(
                    ApplicationError.notFound("GemPurchase", command.gemPurchaseId().toString())
            );
        }

        try {
            gemPurchase.get().reject();
            return Result.success(gemPurchaseRepository.save(gemPurchase.get()));
        } catch (IllegalStateException e) {
            return Result.failure(
                    ApplicationError.businessRuleViolation("GemPurchase rejection", e.getMessage())
            );
        }
    }
}
