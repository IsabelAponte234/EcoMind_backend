package pe.greenminds.ecomind_backend.profile.infrastructure.persistence.jpa.adapters;

import org.springframework.stereotype.Repository;
import pe.greenminds.ecomind_backend.profile.domain.model.aggregates.Friend;
import pe.greenminds.ecomind_backend.profile.domain.model.valueobjects.FriendStatus;
import pe.greenminds.ecomind_backend.profile.domain.repositories.FriendRepository;
import pe.greenminds.ecomind_backend.profile.infrastructure.persistence.jpa.assemblers.FriendPersistenceAssembler;
import pe.greenminds.ecomind_backend.profile.infrastructure.persistence.jpa.repositories.FriendPersistenceRepository;

import java.util.List;
import java.util.Optional;

@Repository
public class FriendRepositoryImpl implements FriendRepository {
    private final FriendPersistenceRepository repository;

    public FriendRepositoryImpl(FriendPersistenceRepository repository) {
        this.repository = repository;
    }

    public List<Friend> findAll() {
        return repository.findAll().stream().map(FriendPersistenceAssembler::toDomainFromPersistence).toList();
    }

    public Optional<Friend> findById(Long id) {
        return repository.findById(id).map(FriendPersistenceAssembler::toDomainFromPersistence);
    }

    public List<Friend> search(Long userId, FriendStatus status) {
        return repository.search(userId, status).stream().map(FriendPersistenceAssembler::toDomainFromPersistence).toList();
    }

    public Friend save(Friend friend) {
        return FriendPersistenceAssembler.toDomainFromPersistence(
                repository.save(FriendPersistenceAssembler.toPersistenceFromDomain(friend)));
    }

    public void deleteById(Long id) { repository.deleteById(id); }
    public void deleteByUserId(Long userId) { repository.deleteByUserIdOrFriendId(userId, userId); }
    public boolean existsById(Long id) { return repository.existsById(id); }
    public boolean existsRelationship(Long userId, Long friendId) { return repository.existsRelationship(userId, friendId); }
}
