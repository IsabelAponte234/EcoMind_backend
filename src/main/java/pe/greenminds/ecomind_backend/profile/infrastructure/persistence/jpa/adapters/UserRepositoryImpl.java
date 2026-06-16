package pe.greenminds.ecomind_backend.profile.infrastructure.persistence.jpa.adapters;

import org.springframework.stereotype.Repository;
import pe.greenminds.ecomind_backend.profile.domain.model.aggregates.User;
import pe.greenminds.ecomind_backend.profile.domain.repositories.UserRepository;
import pe.greenminds.ecomind_backend.profile.infrastructure.persistence.jpa.assemblers.UserPersistenceAssembler;
import pe.greenminds.ecomind_backend.profile.infrastructure.persistence.jpa.repositories.UserPersistenceRepository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private final UserPersistenceRepository repository;

    public UserRepositoryImpl(UserPersistenceRepository repository) {
        this.repository = repository;
    }

    public List<User> findAll() {
        return repository.findAll().stream().map(UserPersistenceAssembler::toDomainFromPersistence).toList();
    }

    public Optional<User> findById(Long id) {
        return repository.findById(id).map(UserPersistenceAssembler::toDomainFromPersistence);
    }

    public User save(User user) {
        return UserPersistenceAssembler.toDomainFromPersistence(
                repository.save(UserPersistenceAssembler.toPersistenceFromDomain(user)));
    }

    public void deleteById(Long id) { repository.deleteById(id); }
    public boolean existsById(Long id) { return repository.existsById(id); }
    public boolean existsByEmail(String email) { return repository.existsByEmail(email); }
}
