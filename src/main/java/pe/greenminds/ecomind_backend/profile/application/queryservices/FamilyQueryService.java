package pe.greenminds.ecomind_backend.profile.application.queryservices;

import pe.greenminds.ecomind_backend.profile.domain.model.aggregates.Family;
import pe.greenminds.ecomind_backend.profile.domain.model.queries.GetAllFamiliesQuery;
import pe.greenminds.ecomind_backend.profile.domain.model.queries.GetFamilyByIdQuery;

import java.util.List;
import java.util.Optional;

public interface FamilyQueryService {
    List<Family> handle(GetAllFamiliesQuery query);
    Optional<Family> handle(GetFamilyByIdQuery query);
}
