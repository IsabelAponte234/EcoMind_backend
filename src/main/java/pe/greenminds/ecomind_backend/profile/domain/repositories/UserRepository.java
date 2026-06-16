package pe.greenminds.ecomind_backend.profile.domain.repositories;

import pe.greenminds.ecomind_backend.profile.domain.model.aggregates.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    List<User> findAll();
    Optional<User> findById(Long id);
    User save(User user);
    void deleteById(Long id);
    boolean existsById(Long id);
    boolean existsByEmail(String email);
}
