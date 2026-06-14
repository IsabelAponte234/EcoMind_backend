package pe.greenminds.ecomind_backend.monetization.application.queryservices;

import pe.greenminds.ecomind_backend.monetization.domain.model.aggregates.GemMovement;
import pe.greenminds.ecomind_backend.monetization.domain.model.queries.GetAllGemMovementsQuery;
import pe.greenminds.ecomind_backend.monetization.domain.model.queries.GetGemMovementByIdQuery;

import java.util.List;
import java.util.Optional;

public interface GemMovementQueryService {
    List<GemMovement> handle(GetAllGemMovementsQuery query);
    Optional<GemMovement> handle(GetGemMovementByIdQuery query);
}
