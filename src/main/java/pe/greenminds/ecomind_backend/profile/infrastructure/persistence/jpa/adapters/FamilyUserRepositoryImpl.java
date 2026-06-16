package pe.greenminds.ecomind_backend.profile.infrastructure.persistence.jpa.adapters;

import org.springframework.stereotype.Repository;
import pe.greenminds.ecomind_backend.profile.domain.model.aggregates.FamilyUser;
import pe.greenminds.ecomind_backend.profile.domain.repositories.FamilyUserRepository;
import pe.greenminds.ecomind_backend.profile.infrastructure.persistence.jpa.assemblers.FamilyUserPersistenceAssembler;
import pe.greenminds.ecomind_backend.profile.infrastructure.persistence.jpa.repositories.FamilyUserPersistenceRepository;

import java.util.List;
import java.util.Optional;

@Repository
public class FamilyUserRepositoryImpl implements FamilyUserRepository {
    private final FamilyUserPersistenceRepository repository;

    public FamilyUserRepositoryImpl(FamilyUserPersistenceRepository repository) {
        this.repository = repository;
    }

    public List<FamilyUser> findAll() {
        return repository.findAll().stream().map(FamilyUserPersistenceAssembler::toDomainFromPersistence).toList();
    }

    public Optional<FamilyUser> findById(Long id) {
        return repository.findById(id).map(FamilyUserPersistenceAssembler::toDomainFromPersistence);
    }

    public List<FamilyUser> findByUserId(Long userId) {
        return repository.findByUserId(userId).stream().map(FamilyUserPersistenceAssembler::toDomainFromPersistence).toList();
    }

    public List<FamilyUser> findByFamilyId(Long familyId) {
        return repository.findByFamilyId(familyId).stream().map(FamilyUserPersistenceAssembler::toDomainFromPersistence).toList();
    }

    public FamilyUser save(FamilyUser familyUser) {
        return FamilyUserPersistenceAssembler.toDomainFromPersistence(
                repository.save(FamilyUserPersistenceAssembler.toPersistenceFromDomain(familyUser)));
    }

    public void deleteById(Long id) { repository.deleteById(id); }
    public void deleteByUserId(Long userId) { repository.deleteByUserId(userId); }
    public void deleteByFamilyId(Long familyId) { repository.deleteByFamilyId(familyId); }
    public boolean existsById(Long id) { return repository.existsById(id); }
    public boolean existsByUserIdAndFamilyId(Long userId, Long familyId) { return repository.existsByUserIdAndFamilyId(userId, familyId); }
    public boolean existsByUserId(Long userId) { return repository.existsByUserId(userId); }
}
