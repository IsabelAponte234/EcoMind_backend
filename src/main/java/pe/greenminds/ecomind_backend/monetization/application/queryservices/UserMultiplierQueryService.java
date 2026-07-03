package pe.greenminds.ecomind_backend.monetization.application.queryservices;

import pe.greenminds.ecomind_backend.monetization.domain.model.aggregates.UserMultiplier;
import pe.greenminds.ecomind_backend.monetization.domain.model.queries.GetAllUserMultipliersQuery;
import pe.greenminds.ecomind_backend.monetization.domain.model.queries.GetUserMultiplierByIdQuery;
import pe.greenminds.ecomind_backend.monetization.domain.model.queries.GetUserMultipliersByUserIdQuery;

import java.util.List;
import java.util.Optional;

public interface UserMultiplierQueryService {

    Optional<UserMultiplier> handle(GetUserMultiplierByIdQuery query);

    List<UserMultiplier> handle(GetAllUserMultipliersQuery query);

    List<UserMultiplier> handle(GetUserMultipliersByUserIdQuery query);
}
