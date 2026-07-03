package pe.greenminds.ecomind_backend.monetization.application.internal.commandservices;

import org.springframework.stereotype.Service;
import pe.greenminds.ecomind_backend.monetization.application.commandservices.GemPackageCommandService;
import pe.greenminds.ecomind_backend.monetization.domain.model.aggregates.GemPackage;
import pe.greenminds.ecomind_backend.monetization.domain.model.commands.CreateGemPackageCommand;
import pe.greenminds.ecomind_backend.monetization.domain.repositories.GemPackageRepository;
import pe.greenminds.ecomind_backend.shared.application.result.ApplicationError;
import pe.greenminds.ecomind_backend.shared.application.result.Result;

@Service
public class GemPackageCommandServiceImpl implements GemPackageCommandService {

    private final GemPackageRepository gemPackageRepository;

    public GemPackageCommandServiceImpl(GemPackageRepository gemPackageRepository) {
        this.gemPackageRepository = gemPackageRepository;
    }

    @Override
    public Result<GemPackage, ApplicationError> handle(CreateGemPackageCommand command) {
        try {
            var gemPackage = new GemPackage(
                    command.name(),
                    command.gemAmount(),
                    command.realPrice(),
                    command.currency()
            );

            return Result.success(gemPackageRepository.save(gemPackage));
        } catch (IllegalArgumentException e) {
            return Result.failure(
                    ApplicationError.validationError("GemPackage", e.getMessage())
            );
        } catch (Exception e) {
            return Result.failure(
                    ApplicationError.unexpected("GemPackage creation", e.getMessage())
            );
        }
    }
}
