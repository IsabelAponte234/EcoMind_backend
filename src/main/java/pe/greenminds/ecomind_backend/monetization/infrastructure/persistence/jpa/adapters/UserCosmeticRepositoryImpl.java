package pe.greenminds.ecomind_backend.monetization.infrastructure.persistence.jpa.adapters;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Repository;
import pe.greenminds.ecomind_backend.monetization.domain.model.aggregates.UserCosmetic;
import pe.greenminds.ecomind_backend.monetization.domain.repositories.UserCosmeticRepository;
import pe.greenminds.ecomind_backend.monetization.infrastructure.persistence.jpa.assemblers.UserCosmeticPersistenceAssembler;
import pe.greenminds.ecomind_backend.monetization.infrastructure.persistence.jpa.repositories.UserCosmeticPersistenceRepository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserCosmeticRepositoryImpl implements UserCosmeticRepository {

    private final UserCosmeticPersistenceRepository userCosmeticPersistenceRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    public UserCosmeticRepositoryImpl(UserCosmeticPersistenceRepository userCosmeticPersistenceRepository, ApplicationEventPublisher applicationEventPublisher) {
        this.userCosmeticPersistenceRepository = userCosmeticPersistenceRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public Optional<UserCosmetic> findById(Long id) {
        return userCosmeticPersistenceRepository.findById(id).map(UserCosmeticPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<UserCosmetic> findAll() {
        return userCosmeticPersistenceRepository.findAll().stream().map(UserCosmeticPersistenceAssembler::toDomainFromPersistence).toList();
    }

    @Override
    public UserCosmetic save(UserCosmetic userCosmetic) {
        boolean isNew = userCosmetic.getId() == null;
        var savedEntity = userCosmeticPersistenceRepository.save(UserCosmeticPersistenceAssembler.toPersistenceFromDomain(userCosmetic));
        var saved = UserCosmeticPersistenceAssembler.toDomainFromPersistence(savedEntity);
        if (isNew) {
            saved.onCreated();
            saved.domainEvents().forEach(applicationEventPublisher::publishEvent);
            saved.clearDomainEvents();
        }
        return saved;
    }

    @Override
    public void deleteById(Long id) {
        userCosmeticPersistenceRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return userCosmeticPersistenceRepository.existsById(id);
    }
}
