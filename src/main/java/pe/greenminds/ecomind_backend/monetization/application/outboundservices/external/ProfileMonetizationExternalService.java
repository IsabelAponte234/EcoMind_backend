package pe.greenminds.ecomind_backend.monetization.application.outboundservices.external;

import org.springframework.stereotype.Service;
import pe.greenminds.ecomind_backend.profile.application.commandservices.UserCommandService;
import pe.greenminds.ecomind_backend.profile.domain.model.commands.CreditUserGemsCommand;
import pe.greenminds.ecomind_backend.profile.domain.model.commands.SpendUserGemsCommand;
import pe.greenminds.ecomind_backend.shared.application.result.ApplicationError;
import pe.greenminds.ecomind_backend.shared.application.result.Result;

@Service
public class ProfileMonetizationExternalService {

    private final UserCommandService userCommandService;

    public ProfileMonetizationExternalService(UserCommandService userCommandService) {
        this.userCommandService = userCommandService;
    }

    public Result<Void, ApplicationError> creditGems(Long userId, Integer amount) {
        return userCommandService.handle(new CreditUserGemsCommand(userId, amount))
                .map(user -> null);
    }

    public Result<Void, ApplicationError> spendGems(Long userId, Integer amount) {
        return userCommandService.handle(new SpendUserGemsCommand(userId, amount))
                .map(user -> null);
    }
}
