package pe.greenminds.ecomind_backend.profile.domain.repositories;

import pe.greenminds.ecomind_backend.profile.domain.model.aggregates.FamilyUser;

import java.util.List;
import java.util.Optional;

public interface FamilyUserRepository {
    List<FamilyUser> findAll();
    Optional<FamilyUser> findById(Long id);
    List<FamilyUser> findByUserId(Long userId);
    List<FamilyUser> findByFamilyId(Long familyId);
    FamilyUser save(FamilyUser familyUser);
    void deleteById(Long id);
    void deleteByUserId(Long userId);
    void deleteByFamilyId(Long familyId);
    boolean existsById(Long id);
    boolean existsByUserIdAndFamilyId(Long userId, Long familyId);
    boolean existsByUserId(Long userId);
}
