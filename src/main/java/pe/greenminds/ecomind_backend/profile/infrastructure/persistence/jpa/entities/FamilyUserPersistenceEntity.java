package pe.greenminds.ecomind_backend.profile.infrastructure.persistence.jpa.entities;

import jakarta.persistence.*;
import pe.greenminds.ecomind_backend.profile.domain.model.valueobjects.FamilyRole;
import pe.greenminds.ecomind_backend.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;

import java.time.OffsetDateTime;

@Entity
@Table(name = "family_users", uniqueConstraints = {
        @UniqueConstraint(name = "uk_family_users_user_family", columnNames = {"user_id", "family_id"})
})
public class FamilyUserPersistenceEntity extends AuditableAbstractPersistenceEntity {
    @Column(name = "user_id", nullable = false)
    private Long userId;
    @Column(name = "family_id", nullable = false)
    private Long familyId;
    @Enumerated(EnumType.STRING)
    @Column(name = "family_role", nullable = false)
    private FamilyRole familyRole;
    @Column(name = "joined_at", nullable = false)
    private OffsetDateTime joinedAt;

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public Long getFamilyId() { return familyId; }
    public void setFamilyId(Long familyId) { this.familyId = familyId; }
    public FamilyRole getFamilyRole() { return familyRole; }
    public void setFamilyRole(FamilyRole familyRole) { this.familyRole = familyRole; }
    public OffsetDateTime getJoinedAt() { return joinedAt; }
    public void setJoinedAt(OffsetDateTime joinedAt) { this.joinedAt = joinedAt; }
}
