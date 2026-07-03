package pe.greenminds.ecomind_backend.monetization.infrastructure.persistence.jpa.adapters;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Repository;
import pe.greenminds.ecomind_backend.monetization.domain.model.aggregates.UserMultiplier;
import pe.greenminds.ecomind_backend.monetization.domain.repositories.UserMultiplierRepository;
import pe.greenminds.ecomind_backend.monetization.infrastructure.persistence.jpa.assemblers.UserMultiplierPersistenceAssembler;
import pe.greenminds.ecomind_backend.monetization.infrastructure.persistence.jpa.repositories.UserMultiplierPersistenceRepository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserMultiplierRepositoryImpl implements UserMultiplierRepository {

    private final UserMultiplierPersistenceRepository userMultiplierPersistenceRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    public UserMultiplierRepositoryImpl(UserMultiplierPersistenceRepository userMultiplierPersistenceRepository, ApplicationEventPublisher applicationEventPublisher) {
        this.userMultiplierPersistenceRepository = userMultiplierPersistenceRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public Optional<UserMultiplier> findById(Long id) {
        return userMultiplierPersistenceRepository.findById(id).map(UserMultiplierPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<UserMultiplier> findAll() {
        return userMultiplierPersistenceRepository.findAll().stream().map(UserMultiplierPersistenceAssembler::toDomainFromPersistence).toList();
    }

    @Override
    public List<UserMultiplier> findByUserId(Long userId) {
        return userMultiplierPersistenceRepository.findByUserId(userId).stream().map(UserMultiplierPersistenceAssembler::toDomainFromPersistence).toList();
    }

    @Override
    public UserMultiplier save(UserMultiplier userMultiplier) {
        boolean isNew = userMultiplier.getId() == null;
        var savedEntity = userMultiplierPersistenceRepository.save(UserMultiplierPersistenceAssembler.toPersistenceFromDomain(userMultiplier));
        var saved = UserMultiplierPersistenceAssembler.toDomainFromPersistence(savedEntity);
        if (isNew) {
            saved.onCreated();
            saved.domainEvents().forEach(applicationEventPublisher::publishEvent);
            saved.clearDomainEvents();
        }
        return saved;
    }

    @Override
    public void deleteById(Long id) {
        userMultiplierPersistenceRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return userMultiplierPersistenceRepository.existsById(id);
    }
}
