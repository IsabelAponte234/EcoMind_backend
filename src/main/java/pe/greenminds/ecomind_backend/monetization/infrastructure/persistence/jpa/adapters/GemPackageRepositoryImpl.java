package pe.greenminds.ecomind_backend.monetization.infrastructure.persistence.jpa.adapters;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Repository;
import pe.greenminds.ecomind_backend.monetization.domain.model.aggregates.GemPackage;
import pe.greenminds.ecomind_backend.monetization.domain.repositories.GemPackageRepository;
import pe.greenminds.ecomind_backend.monetization.infrastructure.persistence.jpa.assemblers.GemPackagePersistenceAssembler;
import pe.greenminds.ecomind_backend.monetization.infrastructure.persistence.jpa.repositories.GemPackagePersistenceRepository;

import java.util.List;
import java.util.Optional;

@Repository
public class GemPackageRepositoryImpl implements GemPackageRepository {

    private final GemPackagePersistenceRepository gemPackagePersistenceRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    public GemPackageRepositoryImpl(GemPackagePersistenceRepository gemPackagePersistenceRepository, ApplicationEventPublisher applicationEventPublisher) {
        this.gemPackagePersistenceRepository = gemPackagePersistenceRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public Optional<GemPackage> findById(Long id) {
        return gemPackagePersistenceRepository.findById(id).map(GemPackagePersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<GemPackage> findAll() {
        return gemPackagePersistenceRepository.findAll().stream().map(GemPackagePersistenceAssembler::toDomainFromPersistence).toList();
    }

    @Override
    public GemPackage save(GemPackage gemPackage) {
        boolean isNew = gemPackage.getId() == null;
        var savedEntity = gemPackagePersistenceRepository.save(GemPackagePersistenceAssembler.toPersistenceFromDomain(gemPackage));
        var saved = GemPackagePersistenceAssembler.toDomainFromPersistence(savedEntity);
        if (isNew) {
            saved.onCreated();
            saved.domainEvents().forEach(applicationEventPublisher::publishEvent);
            saved.clearDomainEvents();
        }
        return saved;
    }

    @Override
    public void deleteById(Long id) {
        gemPackagePersistenceRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return gemPackagePersistenceRepository.existsById(id);
    }
}
