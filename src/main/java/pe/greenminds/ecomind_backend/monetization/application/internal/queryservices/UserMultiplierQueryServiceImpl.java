package pe.greenminds.ecomind_backend.monetization.application.internal.queryservices;

import org.springframework.stereotype.Service;
import pe.greenminds.ecomind_backend.monetization.application.queryservices.UserMultiplierQueryService;
import pe.greenminds.ecomind_backend.monetization.domain.model.aggregates.UserMultiplier;
import pe.greenminds.ecomind_backend.monetization.domain.model.queries.GetAllUserMultipliersQuery;
import pe.greenminds.ecomind_backend.monetization.domain.model.queries.GetUserMultiplierByIdQuery;
import pe.greenminds.ecomind_backend.monetization.domain.model.queries.GetUserMultipliersByUserIdQuery;
import pe.greenminds.ecomind_backend.monetization.domain.repositories.UserMultiplierRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserMultiplierQueryServiceImpl implements UserMultiplierQueryService {

    private final UserMultiplierRepository userMultiplierRepository;

    public UserMultiplierQueryServiceImpl(UserMultiplierRepository userMultiplierRepository) {
        this.userMultiplierRepository = userMultiplierRepository;
    }

    @Override
    public Optional<UserMultiplier> handle(GetUserMultiplierByIdQuery query) {
        return userMultiplierRepository.findById(query.id());
    }

    @Override
    public List<UserMultiplier> handle(GetAllUserMultipliersQuery query) {
        return userMultiplierRepository.findAll();
    }

    @Override
    public List<UserMultiplier> handle(GetUserMultipliersByUserIdQuery query) {
        return userMultiplierRepository.findByUserId(query.userId());
    }
}
