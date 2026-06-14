package pe.greenminds.ecomind_backend.monetization.infrastructure.persistence.jpa.adapters;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Repository;
import pe.greenminds.ecomind_backend.monetization.domain.model.aggregates.GemMovement;
import pe.greenminds.ecomind_backend.monetization.domain.repositories.GemMovementRepository;
import pe.greenminds.ecomind_backend.monetization.infrastructure.persistence.jpa.assemblers.GemMovementPersistenceAssembler;
import pe.greenminds.ecomind_backend.monetization.infrastructure.persistence.jpa.repositories.GemMovementPersistenceRepository;

import java.util.List;
import java.util.Optional;

@Repository
public class GemMovementRepositoryImpl implements GemMovementRepository {

    private final GemMovementPersistenceRepository gemMovementPersistenceRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    public GemMovementRepositoryImpl(GemMovementPersistenceRepository gemMovementPersistenceRepository, ApplicationEventPublisher applicationEventPublisher) {
        this.gemMovementPersistenceRepository = gemMovementPersistenceRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public Optional<GemMovement> findById(Long id) {
        return gemMovementPersistenceRepository.findById(id).map(GemMovementPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<GemMovement> findAll() {
        return gemMovementPersistenceRepository.findAll().stream().map(GemMovementPersistenceAssembler::toDomainFromPersistence).toList();
    }

    @Override
    public GemMovement save(GemMovement gemMovement) {
        boolean isNew = gemMovement.getId() == null;
        var savedEntity = gemMovementPersistenceRepository.save(GemMovementPersistenceAssembler.toPersistenceFromDomain(gemMovement));
        var saved = GemMovementPersistenceAssembler.toDomainFromPersistence(savedEntity);
        if (isNew) {
            saved.onCreated();
            saved.domainEvents().forEach(applicationEventPublisher::publishEvent);
            saved.clearDomainEvents();
        }
        return saved;
    }

    @Override
    public void deleteById(Long id) {
        gemMovementPersistenceRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return gemMovementPersistenceRepository.existsById(id);
    }
}
