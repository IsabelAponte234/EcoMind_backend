package pe.greenminds.ecomind_backend.profile.application.internal.queryservices;

import org.springframework.stereotype.Service;
import pe.greenminds.ecomind_backend.profile.application.queryservices.FamilyQueryService;
import pe.greenminds.ecomind_backend.profile.domain.model.aggregates.Family;
import pe.greenminds.ecomind_backend.profile.domain.model.queries.GetAllFamiliesQuery;
import pe.greenminds.ecomind_backend.profile.domain.model.queries.GetFamilyByIdQuery;
import pe.greenminds.ecomind_backend.profile.domain.repositories.FamilyRepository;

import java.util.List;
import java.util.Optional;

@Service
public class FamilyQueryServiceImpl implements FamilyQueryService {
    private final FamilyRepository repository;

    public FamilyQueryServiceImpl(FamilyRepository repository) {
        this.repository = repository;
    }

    public List<Family> handle(GetAllFamiliesQuery query) { return repository.findAll(); }
    public Optional<Family> handle(GetFamilyByIdQuery query) { return repository.findById(query.familyId()); }
}
