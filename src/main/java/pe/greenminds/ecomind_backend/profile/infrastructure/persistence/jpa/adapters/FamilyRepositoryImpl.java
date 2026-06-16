package pe.greenminds.ecomind_backend.profile.infrastructure.persistence.jpa.adapters;

import org.springframework.stereotype.Repository;
import pe.greenminds.ecomind_backend.profile.domain.model.aggregates.Family;
import pe.greenminds.ecomind_backend.profile.domain.repositories.FamilyRepository;
import pe.greenminds.ecomind_backend.profile.infrastructure.persistence.jpa.assemblers.FamilyPersistenceAssembler;
import pe.greenminds.ecomind_backend.profile.infrastructure.persistence.jpa.repositories.FamilyPersistenceRepository;

import java.util.List;
import java.util.Optional;

@Repository
public class FamilyRepositoryImpl implements FamilyRepository {
    private final FamilyPersistenceRepository repository;

    public FamilyRepositoryImpl(FamilyPersistenceRepository repository) {
        this.repository = repository;
    }

    public List<Family> findAll() {
        return repository.findAll().stream().map(FamilyPersistenceAssembler::toDomainFromPersistence).toList();
    }

    public Optional<Family> findById(Long id) {
        return repository.findById(id).map(FamilyPersistenceAssembler::toDomainFromPersistence);
    }

    public Family save(Family family) {
        return FamilyPersistenceAssembler.toDomainFromPersistence(
                repository.save(FamilyPersistenceAssembler.toPersistenceFromDomain(family)));
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return repository.existsById(id);
    }
}
