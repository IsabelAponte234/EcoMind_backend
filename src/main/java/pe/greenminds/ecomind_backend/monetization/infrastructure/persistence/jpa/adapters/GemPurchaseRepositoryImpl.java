package pe.greenminds.ecomind_backend.monetization.infrastructure.persistence.jpa.adapters;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Repository;
import pe.greenminds.ecomind_backend.monetization.domain.model.aggregates.GemPurchase;
import pe.greenminds.ecomind_backend.monetization.domain.repositories.GemPurchaseRepository;
import pe.greenminds.ecomind_backend.monetization.infrastructure.persistence.jpa.assemblers.GemPurchasePersistenceAssembler;
import pe.greenminds.ecomind_backend.monetization.infrastructure.persistence.jpa.repositories.GemPurchasePersistenceRepository;

import java.util.List;
import java.util.Optional;

@Repository
public class GemPurchaseRepositoryImpl implements GemPurchaseRepository {

    private final GemPurchasePersistenceRepository gemPurchasePersistenceRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    public GemPurchaseRepositoryImpl(GemPurchasePersistenceRepository gemPurchasePersistenceRepository, ApplicationEventPublisher applicationEventPublisher) {
        this.gemPurchasePersistenceRepository = gemPurchasePersistenceRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public Optional<GemPurchase> findById(Long id) {
        return gemPurchasePersistenceRepository.findById(id).map(GemPurchasePersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<GemPurchase> findAll() {
        return gemPurchasePersistenceRepository.findAll().stream().map(GemPurchasePersistenceAssembler::toDomainFromPersistence).toList();
    }

    @Override
    public List<GemPurchase> findByUserId(Long userId) {
        return gemPurchasePersistenceRepository.findByUserId(userId).stream().map(GemPurchasePersistenceAssembler::toDomainFromPersistence).toList();
    }

    @Override
    public Optional<GemPurchase> findByPaymentReference(String paymentReference) {
        return gemPurchasePersistenceRepository.findByPaymentReference(paymentReference).map(GemPurchasePersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public GemPurchase save(GemPurchase gemPurchase) {
        boolean isNew = gemPurchase.getId() == null;
        var savedEntity = gemPurchasePersistenceRepository.save(GemPurchasePersistenceAssembler.toPersistenceFromDomain(gemPurchase));
        var saved = GemPurchasePersistenceAssembler.toDomainFromPersistence(savedEntity);
        if (isNew) {
            saved.onCreated();
            saved.domainEvents().forEach(applicationEventPublisher::publishEvent);
            saved.clearDomainEvents();
        }
        return saved;
    }

    @Override
    public void deleteById(Long id) {
        gemPurchasePersistenceRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return gemPurchasePersistenceRepository.existsById(id);
    }
}
