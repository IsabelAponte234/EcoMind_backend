package pe.greenminds.ecomind_backend.profile.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.greenminds.ecomind_backend.profile.domain.model.valueobjects.InvitationStatus;
import pe.greenminds.ecomind_backend.profile.infrastructure.persistence.jpa.entities.FamilyInvitationPersistenceEntity;

import java.util.List;

@Repository
public interface FamilyInvitationPersistenceRepository extends JpaRepository<FamilyInvitationPersistenceEntity, Long> {
    @Query("""
            SELECT i FROM FamilyInvitationPersistenceEntity i
            WHERE (:invitedUserId IS NULL OR i.invitedUserId = :invitedUserId)
              AND (:inviterUserId IS NULL OR i.inviterUserId = :inviterUserId)
              AND (:status IS NULL OR i.status = :status)
            """)
    List<FamilyInvitationPersistenceEntity> search(
            @Param("invitedUserId") Long invitedUserId,
            @Param("inviterUserId") Long inviterUserId,
            @Param("status") InvitationStatus status
    );

    void deleteByFamilyId(Long familyId);
}
