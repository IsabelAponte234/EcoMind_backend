package pe.greenminds.ecomind_backend.profile.infrastructure.persistence.jpa.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import pe.greenminds.ecomind_backend.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;

import java.time.LocalDate;
import java.time.OffsetDateTime;

@Entity
@Table(name = "users")
public class UserPersistenceEntity extends AuditableAbstractPersistenceEntity {
    @Column(name = "community_id")
    private Long communityId;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(name = "birth_date")
    private LocalDate birthDate;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private Integer streak;
    @Column(length = 1000)
    private String commitment;
    @Column(name = "registered_at", nullable = false)
    private OffsetDateTime registeredAt;
    @Column(name = "gem_balance", nullable = false)
    private Integer gemBalance;
    @Column(nullable = false)
    private Integer ecopoints;
    @Column(name = "last_streak_date")
    private LocalDate lastStreakDate;

    public Long getCommunityId() { return communityId; }
    public void setCommunityId(Long communityId) { this.communityId = communityId; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public LocalDate getBirthDate() { return birthDate; }
    public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Integer getStreak() { return streak; }
    public void setStreak(Integer streak) { this.streak = streak; }
    public String getCommitment() { return commitment; }
    public void setCommitment(String commitment) { this.commitment = commitment; }
    public OffsetDateTime getRegisteredAt() { return registeredAt; }
    public void setRegisteredAt(OffsetDateTime registeredAt) { this.registeredAt = registeredAt; }
    public Integer getGemBalance() { return gemBalance; }
    public void setGemBalance(Integer gemBalance) { this.gemBalance = gemBalance; }
    public Integer getEcopoints() { return ecopoints; }
    public void setEcopoints(Integer ecopoints) { this.ecopoints = ecopoints; }
    public LocalDate getLastStreakDate() { return lastStreakDate; }
    public void setLastStreakDate(LocalDate lastStreakDate) { this.lastStreakDate = lastStreakDate; }
}
