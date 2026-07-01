package pe.greenminds.ecomind_backend.monetization.application.queryservices;

import pe.greenminds.ecomind_backend.monetization.domain.model.aggregates.UserCosmetic;
import pe.greenminds.ecomind_backend.monetization.domain.model.queries.GetAllUserCosmeticsQuery;
import pe.greenminds.ecomind_backend.monetization.domain.model.queries.GetUserCosmeticByIdQuery;
import pe.greenminds.ecomind_backend.monetization.domain.model.queries.GetUserCosmeticsByUserIdQuery;

import java.util.List;
import java.util.Optional;

public interface UserCosmeticQueryService {

    Optional<UserCosmetic> handle(GetUserCosmeticByIdQuery query);

    List<UserCosmetic> handle(GetAllUserCosmeticsQuery query);

    List<UserCosmetic> handle(GetUserCosmeticsByUserIdQuery query);
}
