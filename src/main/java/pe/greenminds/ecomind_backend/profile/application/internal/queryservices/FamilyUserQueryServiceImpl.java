package pe.greenminds.ecomind_backend.profile.application.internal.queryservices;

import org.springframework.stereotype.Service;
import pe.greenminds.ecomind_backend.profile.application.queryservices.FamilyUserQueryService;
import pe.greenminds.ecomind_backend.profile.domain.model.aggregates.FamilyUser;
import pe.greenminds.ecomind_backend.profile.domain.model.queries.GetFamilyUsersQuery;
import pe.greenminds.ecomind_backend.profile.domain.repositories.FamilyUserRepository;

import java.util.List;

@Service
public class FamilyUserQueryServiceImpl implements FamilyUserQueryService {
    private final FamilyUserRepository repository;

    public FamilyUserQueryServiceImpl(FamilyUserRepository repository) {
        this.repository = repository;
    }

    public List<FamilyUser> handle(GetFamilyUsersQuery query) {
        if (query.userId() != null) return repository.findByUserId(query.userId());
        if (query.familyId() != null) return repository.findByFamilyId(query.familyId());
        return repository.findAll();
    }
}
