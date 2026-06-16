package pe.greenminds.ecomind_backend.profile.application.internal.queryservices;

import org.springframework.stereotype.Service;
import pe.greenminds.ecomind_backend.profile.application.queryservices.UserQueryService;
import pe.greenminds.ecomind_backend.profile.domain.model.aggregates.User;
import pe.greenminds.ecomind_backend.profile.domain.model.queries.GetAllUsersQuery;
import pe.greenminds.ecomind_backend.profile.domain.model.queries.GetUserByIdQuery;
import pe.greenminds.ecomind_backend.profile.domain.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserQueryServiceImpl implements UserQueryService {
    private final UserRepository repository;

    public UserQueryServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    public List<User> handle(GetAllUsersQuery query) { return repository.findAll(); }
    public Optional<User> handle(GetUserByIdQuery query) { return repository.findById(query.userId()); }
}
