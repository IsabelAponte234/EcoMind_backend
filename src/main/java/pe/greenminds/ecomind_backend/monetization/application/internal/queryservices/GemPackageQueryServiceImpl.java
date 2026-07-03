package pe.greenminds.ecomind_backend.monetization.application.internal.queryservices;

import org.springframework.stereotype.Service;
import pe.greenminds.ecomind_backend.monetization.application.queryservices.GemPackageQueryService;
import pe.greenminds.ecomind_backend.monetization.domain.model.aggregates.GemPackage;
import pe.greenminds.ecomind_backend.monetization.domain.model.queries.GetAllGemPackagesQuery;
import pe.greenminds.ecomind_backend.monetization.domain.model.queries.GetGemPackageByIdQuery;
import pe.greenminds.ecomind_backend.monetization.domain.repositories.GemPackageRepository;

import java.util.List;
import java.util.Optional;

@Service
public class GemPackageQueryServiceImpl implements GemPackageQueryService {

    private final GemPackageRepository gemPackageRepository;

    public GemPackageQueryServiceImpl(GemPackageRepository gemPackageRepository) {
        this.gemPackageRepository = gemPackageRepository;
    }

    @Override
    public Optional<GemPackage> handle(GetGemPackageByIdQuery query) {
        return gemPackageRepository.findById(query.id());
    }

    @Override
    public List<GemPackage> handle(GetAllGemPackagesQuery query) {
        return gemPackageRepository.findAll();
    }
}
