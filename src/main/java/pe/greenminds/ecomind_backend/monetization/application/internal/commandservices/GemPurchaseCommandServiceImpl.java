package pe.greenminds.ecomind_backend.monetization.application.internal.commandservices;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import pe.greenminds.ecomind_backend.monetization.application.commandservices.GemPurchaseCommandService;
import pe.greenminds.ecomind_backend.monetization.domain.model.aggregates.GemPurchase;
import pe.greenminds.ecomind_backend.monetization.domain.model.commands.CreateGemPurchaseCommand;
import pe.greenminds.ecomind_backend.monetization.domain.repositories.GemPackageRepository;
import pe.greenminds.ecomind_backend.monetization.domain.repositories.GemPurchaseRepository;
import pe.greenminds.ecomind_backend.shared.application.result.ApplicationError;
import pe.greenminds.ecomind_backend.shared.application.result.Result;

@Service
public class GemPurchaseCommandServiceImpl implements GemPurchaseCommandService {

    private final GemPurchaseRepository gemPurchaseRepository;
    private final GemPackageRepository gemPackageRepository;

    public GemPurchaseCommandServiceImpl(GemPurchaseRepository gemPurchaseRepository, GemPackageRepository gemPackageRepository) {
        this.gemPurchaseRepository = gemPurchaseRepository;
        this.gemPackageRepository = gemPackageRepository;
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
}
