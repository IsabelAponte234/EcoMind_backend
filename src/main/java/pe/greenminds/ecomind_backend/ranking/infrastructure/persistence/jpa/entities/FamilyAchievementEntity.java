package pe.greenminds.ecomind_backend.ranking.infrastructure.persistence.jpa.entities;

import jakarta.persistence.*;
import pe.greenminds.ecomind_backend.shared.infrastructure.persistence.jpa.AuditableAbstractPersistenceEntity;

import java.util.Date;

@Entity
@Table(
        name = "family_achievements",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_family_achievement_family_achievement",
                        columnNames = {"family_id", "achievement_id"}
                )
        }
)
public class FamilyAchievementEntity extends AuditableAbstractPersistenceEntity {

    @Column(name = "family_id", nullable = false)
    private Long familyId;

    @Column(name = "achievement_id", nullable = false)
    private Long achievementId;

    @Column(name = "earned_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date earnedAt;

    protected FamilyAchievementEntity() {}

    public FamilyAchievementEntity(Long familyId, Long achievementId) {
        this.familyId = familyId;
        this.achievementId = achievementId;
        this.earnedAt = new Date();
    }

    public Long getFamilyId() { return familyId; }
    public Long getAchievementId() { return achievementId; }
    public Date getEarnedAt() { return earnedAt; }
}
