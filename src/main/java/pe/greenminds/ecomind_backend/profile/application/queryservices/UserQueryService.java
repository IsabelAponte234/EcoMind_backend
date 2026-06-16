package pe.greenminds.ecomind_backend.profile.application.queryservices;

import pe.greenminds.ecomind_backend.profile.domain.model.aggregates.User;
import pe.greenminds.ecomind_backend.profile.domain.model.queries.GetAllUsersQuery;
import pe.greenminds.ecomind_backend.profile.domain.model.queries.GetUserByIdQuery;

import java.util.List;
import java.util.Optional;

public interface UserQueryService {
    List<User> handle(GetAllUsersQuery query);
    Optional<User> handle(GetUserByIdQuery query);
}
