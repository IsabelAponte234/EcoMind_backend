package pe.greenminds.ecomind_backend.monetization.application.internal.queryservices;

import org.springframework.stereotype.Service;
import pe.greenminds.ecomind_backend.monetization.application.queryservices.GemPurchaseQueryService;
import pe.greenminds.ecomind_backend.monetization.domain.model.aggregates.GemPurchase;
import pe.greenminds.ecomind_backend.monetization.domain.model.queries.GetAllGemPurchasesQuery;
import pe.greenminds.ecomind_backend.monetization.domain.model.queries.GetGemPurchaseByIdQuery;
import pe.greenminds.ecomind_backend.monetization.domain.model.queries.GetGemPurchasesByUserIdQuery;
import pe.greenminds.ecomind_backend.monetization.domain.repositories.GemPurchaseRepository;

import java.util.List;
import java.util.Optional;

@Service
public class GemPurchaseQueryServiceImpl implements GemPurchaseQueryService {

    private final GemPurchaseRepository gemPurchaseRepository;

    public GemPurchaseQueryServiceImpl(GemPurchaseRepository gemPurchaseRepository) {
        this.gemPurchaseRepository = gemPurchaseRepository;
    }

    @Override
    public Optional<GemPurchase> handle(GetGemPurchaseByIdQuery query) {
        return gemPurchaseRepository.findById(query.id());
    }

    @Override
    public List<GemPurchase> handle(GetAllGemPurchasesQuery query) {
        return gemPurchaseRepository.findAll();
    }

    @Override
    public List<GemPurchase> handle(GetGemPurchasesByUserIdQuery query) {
        return gemPurchaseRepository.findByUserId(query.userId());
    }
}
