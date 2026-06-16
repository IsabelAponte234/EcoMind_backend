package pe.greenminds.ecomind_backend.profile.domain.repositories;

import pe.greenminds.ecomind_backend.profile.domain.model.aggregates.FamilyInvitation;
import pe.greenminds.ecomind_backend.profile.domain.model.valueobjects.InvitationStatus;

import java.util.List;
import java.util.Optional;

public interface FamilyInvitationRepository {
    List<FamilyInvitation> findAll();
    Optional<FamilyInvitation> findById(Long id);
    List<FamilyInvitation> search(Long invitedUserId, Long inviterUserId, InvitationStatus status);
    FamilyInvitation save(FamilyInvitation invitation);
    void deleteById(Long id);
    void deleteByFamilyId(Long familyId);
    boolean existsById(Long id);
}
