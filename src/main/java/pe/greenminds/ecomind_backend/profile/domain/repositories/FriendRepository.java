package pe.greenminds.ecomind_backend.profile.domain.repositories;

import pe.greenminds.ecomind_backend.profile.domain.model.aggregates.Friend;
import pe.greenminds.ecomind_backend.profile.domain.model.valueobjects.FriendStatus;

import java.util.List;
import java.util.Optional;

public interface FriendRepository {
    List<Friend> findAll();
    Optional<Friend> findById(Long id);
    List<Friend> search(Long userId, FriendStatus status);
    Friend save(Friend friend);
    void deleteById(Long id);
    void deleteByUserId(Long userId);
    boolean existsById(Long id);
    boolean existsRelationship(Long userId, Long friendId);
}
