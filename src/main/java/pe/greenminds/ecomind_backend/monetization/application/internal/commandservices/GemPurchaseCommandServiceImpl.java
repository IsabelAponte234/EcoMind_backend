package pe.greenminds.ecomind_backend.monetization.application.internal.commandservices;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import pe.greenminds.ecomind_backend.monetization.application.commandservices.GemPurchaseCommandService;
import pe.greenminds.ecomind_backend.monetization.domain.model.aggregates.GemPurchase;
import pe.greenminds.ecomind_backend.monetization.domain.model.commands.ApproveGemPurchaseCommand;
import pe.greenminds.ecomind_backend.monetization.domain.model.commands.ConfirmGemPurchaseByChargeCommand;
import pe.greenminds.ecomind_backend.monetization.domain.model.commands.CreateGemPurchaseCheckoutCommand;
import pe.greenminds.ecomind_backend.monetization.domain.model.commands.CreateGemPurchaseCommand;
import pe.greenminds.ecomind_backend.monetization.application.outboundservices.external.ProfileMonetizationExternalService;
import pe.greenminds.ecomind_backend.monetization.application.outboundservices.gateway.ChargeRequest;
import pe.greenminds.ecomind_backend.monetization.application.outboundservices.gateway.PaymentGatewayResolver;
import pe.greenminds.ecomind_backend.monetization.domain.model.aggregates.GemMovement;
import pe.greenminds.ecomind_backend.monetization.domain.model.aggregates.GemPackage;
import pe.greenminds.ecomind_backend.monetization.domain.model.commands.PayGemPurchaseCommand;
import pe.greenminds.ecomind_backend.monetization.domain.model.commands.RejectGemPurchaseCommand;
import pe.greenminds.ecomind_backend.monetization.domain.model.valueobjects.MovementOrigin;
import pe.greenminds.ecomind_backend.monetization.domain.model.valueobjects.MovementType;
import pe.greenminds.ecomind_backend.monetization.domain.model.valueobjects.PaymentStatus;
import pe.greenminds.ecomind_backend.monetization.domain.repositories.GemMovementRepository;
import pe.greenminds.ecomind_backend.monetization.domain.repositories.GemPackageRepository;
import pe.greenminds.ecomind_backend.monetization.domain.repositories.GemPurchaseRepository;
import pe.greenminds.ecomind_backend.shared.application.result.ApplicationError;
import pe.greenminds.ecomind_backend.shared.application.result.Result;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class GemPurchaseCommandServiceImpl implements GemPurchaseCommandService {

    private final GemPurchaseRepository gemPurchaseRepository;
    private final GemPackageRepository gemPackageRepository;
    private final GemMovementRepository gemMovementRepository;
    private final ProfileMonetizationExternalService profileMonetizationExternalService;
    private final PaymentGatewayResolver paymentGatewayResolver;

    public GemPurchaseCommandServiceImpl(
            GemPurchaseRepository gemPurchaseRepository,
            GemPackageRepository gemPackageRepository,
            GemMovementRepository gemMovementRepository,
            ProfileMonetizationExternalService profileMonetizationExternalService,
            PaymentGatewayResolver paymentGatewayResolver
    ) {
        this.gemPurchaseRepository = gemPurchaseRepository;
        this.gemPackageRepository = gemPackageRepository;
        this.gemMovementRepository = gemMovementRepository;
        this.profileMonetizationExternalService = profileMonetizationExternalService;
        this.paymentGatewayResolver = paymentGatewayResolver;
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
                    command.paymentReference(),
                    command.paymentMethod()
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
                    "PENDING-" + UUID.randomUUID(),
                    command.paymentMethod()
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

        return approveAndCredit(gemPurchase.get(), gemPackage.get());
    }

    @Transactional
    @Override
    public Result<GemPurchase, ApplicationError> handle(PayGemPurchaseCommand command) {
        var gemPurchase = gemPurchaseRepository.findById(command.gemPurchaseId());
        if (gemPurchase.isEmpty()) {
            return Result.failure(
                    ApplicationError.notFound("GemPurchase", command.gemPurchaseId().toString())
            );
        }

        if (gemPurchase.get().getPaymentStatus() != PaymentStatus.PENDING) {
            return Result.failure(
                    ApplicationError.businessRuleViolation("GemPurchase payment", "Gem purchase is not PENDING")
            );
        }

        var gemPackage = gemPackageRepository.findById(gemPurchase.get().getPackageId());
        if (gemPackage.isEmpty()) {
            return Result.failure(
                    ApplicationError.notFound("GemPackage", gemPurchase.get().getPackageId().toString())
            );
        }

        // Pick the gateway that handles this purchase's payment method (Culqi for card/yape, PayPal for paypal).
        var gateway = paymentGatewayResolver.resolve(gemPurchase.get().getPaymentMethod());
        if (gateway.isEmpty()) {
            return Result.failure(
                    ApplicationError.businessRuleViolation(
                            "GemPurchase payment",
                            "No payment gateway available for method " + gemPurchase.get().getPaymentMethod()
                    )
            );
        }

        // Charge the real money through the selected payment gateway.
        var amountInCents = gemPurchase.get().getAmountPaid()
                .multiply(BigDecimal.valueOf(100))
                .intValueExact();
        var chargeResult = gateway.get().charge(new ChargeRequest(
                amountInCents,
                gemPackage.get().getCurrency(),
                command.email(),
                command.sourceToken(),
                "EcoMind - " + gemPackage.get().getName()
        ));

        if (!chargeResult.approved()) {
            gemPurchase.get().reject();
            gemPurchaseRepository.save(gemPurchase.get());
            return Result.failure(
                    ApplicationError.businessRuleViolation("Payment declined", chargeResult.message())
            );
        }

        // Payment approved: store the gateway charge reference, then credit the gems
        // and record the movement atomically.
        gemPurchase.get().assignPaymentReference(chargeResult.chargeId());
        return approveAndCredit(gemPurchase.get(), gemPackage.get());
    }

    /**
     * Marks a PENDING gem purchase as APPROVED, credits the user's gem balance and
     * records the PURCHASE gem movement. Runs inside the caller's transaction, so any
     * failure (e.g. the user no longer exists) rolls everything back.
     */
    private Result<GemPurchase, ApplicationError> approveAndCredit(GemPurchase gemPurchase, GemPackage gemPackage) {
        try {
            gemPurchase.approve();
        } catch (IllegalStateException e) {
            return Result.failure(
                    ApplicationError.businessRuleViolation("GemPurchase approval", e.getMessage())
            );
        }

        var creditResult = profileMonetizationExternalService.creditGems(
                gemPurchase.getUserId(),
                gemPackage.getGemAmount()
        );
        if (creditResult instanceof Result.Failure<Void, ApplicationError> failure) {
            return Result.failure(failure.error());
        }

        var savedGemPurchase = gemPurchaseRepository.save(gemPurchase);

        gemMovementRepository.save(new GemMovement(
                savedGemPurchase.getUserId(),
                MovementType.PURCHASE,
                gemPackage.getGemAmount(),
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

    @Transactional
    @Override
    public Result<GemPurchase, ApplicationError> handle(ConfirmGemPurchaseByChargeCommand command) {
        var gemPurchase = gemPurchaseRepository.findByPaymentReference(command.chargeReference());
        if (gemPurchase.isEmpty()) {
            return Result.failure(
                    ApplicationError.notFound("GemPurchase", command.chargeReference())
            );
        }

        // Idempotent: if the purchase was already approved/rejected (e.g. the
        // synchronous /pay already handled it), the webhook is a no-op.
        if (gemPurchase.get().getPaymentStatus() != PaymentStatus.PENDING) {
            return Result.success(gemPurchase.get());
        }

        if (!command.approved()) {
            gemPurchase.get().reject();
            return Result.success(gemPurchaseRepository.save(gemPurchase.get()));
        }

        var gemPackage = gemPackageRepository.findById(gemPurchase.get().getPackageId());
        if (gemPackage.isEmpty()) {
            return Result.failure(
                    ApplicationError.notFound("GemPackage", gemPurchase.get().getPackageId().toString())
            );
        }

        return approveAndCredit(gemPurchase.get(), gemPackage.get());
    }
}
