package pe.greenminds.ecomind_backend.monetization.application.queryservices;

import pe.greenminds.ecomind_backend.monetization.domain.model.aggregates.GemPurchase;
import pe.greenminds.ecomind_backend.monetization.domain.model.queries.GetAllGemPurchasesQuery;
import pe.greenminds.ecomind_backend.monetization.domain.model.queries.GetGemPurchaseByIdQuery;

import java.util.List;
import java.util.Optional;

public interface GemPurchaseQueryService {

    Optional<GemPurchase> handle(GetGemPurchaseByIdQuery query);

    List<GemPurchase> handle(GetAllGemPurchasesQuery query);
}
