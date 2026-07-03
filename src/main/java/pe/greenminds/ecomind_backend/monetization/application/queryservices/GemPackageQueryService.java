package pe.greenminds.ecomind_backend.monetization.application.queryservices;

import pe.greenminds.ecomind_backend.monetization.domain.model.aggregates.GemPackage;
import pe.greenminds.ecomind_backend.monetization.domain.model.queries.GetAllGemPackagesQuery;
import pe.greenminds.ecomind_backend.monetization.domain.model.queries.GetGemPackageByIdQuery;

import java.util.List;
import java.util.Optional;

public interface GemPackageQueryService {

    Optional<GemPackage> handle(GetGemPackageByIdQuery query);

    List<GemPackage> handle(GetAllGemPackagesQuery query);
}
