package pe.greenminds.ecomind_backend.monetization.application.internal.queryservices;

import org.springframework.stereotype.Service;
import pe.greenminds.ecomind_backend.monetization.application.queryservices.UserCosmeticQueryService;
import pe.greenminds.ecomind_backend.monetization.domain.model.aggregates.UserCosmetic;
import pe.greenminds.ecomind_backend.monetization.domain.model.queries.GetAllUserCosmeticsQuery;
import pe.greenminds.ecomind_backend.monetization.domain.model.queries.GetUserCosmeticByIdQuery;
import pe.greenminds.ecomind_backend.monetization.domain.model.queries.GetUserCosmeticsByUserIdQuery;
import pe.greenminds.ecomind_backend.monetization.domain.repositories.UserCosmeticRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserCosmeticQueryServiceImpl implements UserCosmeticQueryService {

    private final UserCosmeticRepository userCosmeticRepository;

    public UserCosmeticQueryServiceImpl(UserCosmeticRepository userCosmeticRepository) {
        this.userCosmeticRepository = userCosmeticRepository;
    }

    @Override
    public Optional<UserCosmetic> handle(GetUserCosmeticByIdQuery query) {
        return userCosmeticRepository.findById(query.id());
    }

    @Override
    public List<UserCosmetic> handle(GetAllUserCosmeticsQuery query) {
        return userCosmeticRepository.findAll();
    }

    @Override
    public List<UserCosmetic> handle(GetUserCosmeticsByUserIdQuery query) {
        return userCosmeticRepository.findByUserId(query.userId());
    }
}
