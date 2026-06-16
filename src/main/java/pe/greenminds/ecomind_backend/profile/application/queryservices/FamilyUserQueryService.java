package pe.greenminds.ecomind_backend.profile.application.queryservices;

import pe.greenminds.ecomind_backend.profile.domain.model.aggregates.FamilyUser;
import pe.greenminds.ecomind_backend.profile.domain.model.queries.GetFamilyUsersQuery;

import java.util.List;

public interface FamilyUserQueryService {
    List<FamilyUser> handle(GetFamilyUsersQuery query);
}
