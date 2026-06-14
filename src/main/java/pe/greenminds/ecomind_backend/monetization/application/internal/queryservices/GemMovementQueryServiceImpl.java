package pe.greenminds.ecomind_backend.monetization.application.internal.queryservices;

import org.springframework.stereotype.Service;
import pe.greenminds.ecomind_backend.monetization.domain.model.aggregates.GemMovement;
import pe.greenminds.ecomind_backend.monetization.domain.model.queries.GetAllGemMovementsQuery;
import pe.greenminds.ecomind_backend.monetization.domain.model.queries.GetGemMovementByIdQuery;
import pe.greenminds.ecomind_backend.monetization.domain.repositories.GemMovementRepository;
import pe.greenminds.ecomind_backend.monetization.application.queryservices.GemMovementQueryService;

import java.util.List;
import java.util.Optional;

@Service
public class GemMovementQueryServiceImpl implements GemMovementQueryService {

    private final GemMovementRepository gemMovementRepository;

    public GemMovementQueryServiceImpl(GemMovementRepository gemMovementRepository) {
        this.gemMovementRepository = gemMovementRepository;
    }

    @Override
    public List<GemMovement> handle(GetAllGemMovementsQuery query) {
        return gemMovementRepository.findAll();
    }

    @Override
    public Optional<GemMovement> handle(GetGemMovementByIdQuery query) {
        return gemMovementRepository.findById(query.id());
    }
}
