package pe.greenminds.ecomind_backend.monetization.application.commandservices;

import pe.greenminds.ecomind_backend.monetization.domain.model.aggregates.GemPurchase;
import pe.greenminds.ecomind_backend.monetization.domain.model.commands.ApproveGemPurchaseCommand;
import pe.greenminds.ecomind_backend.monetization.domain.model.commands.ConfirmGemPurchaseByChargeCommand;
import pe.greenminds.ecomind_backend.monetization.domain.model.commands.CreateGemPurchaseCheckoutCommand;
import pe.greenminds.ecomind_backend.monetization.domain.model.commands.CreateGemPurchaseCommand;
import pe.greenminds.ecomind_backend.monetization.domain.model.commands.PayGemPurchaseCommand;
import pe.greenminds.ecomind_backend.monetization.domain.model.commands.RejectGemPurchaseCommand;
import pe.greenminds.ecomind_backend.shared.application.result.ApplicationError;
import pe.greenminds.ecomind_backend.shared.application.result.Result;

public interface GemPurchaseCommandService {

    Result<GemPurchase, ApplicationError> handle(CreateGemPurchaseCommand command);

    Result<GemPurchase, ApplicationError> handle(CreateGemPurchaseCheckoutCommand command);

    Result<GemPurchase, ApplicationError> handle(PayGemPurchaseCommand command);

    Result<GemPurchase, ApplicationError> handle(ApproveGemPurchaseCommand command);

    Result<GemPurchase, ApplicationError> handle(RejectGemPurchaseCommand command);

    Result<GemPurchase, ApplicationError> handle(ConfirmGemPurchaseByChargeCommand command);
}
