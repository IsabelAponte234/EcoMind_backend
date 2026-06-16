package pe.greenminds.ecomind_backend.profile.domain.model.aggregates;

import lombok.Getter;
import lombok.Setter;
import pe.greenminds.ecomind_backend.shared.domain.model.aggregates.AbstractDomainAggregateRoot;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Objects;

public class User extends AbstractDomainAggregateRoot<User> {
    @Getter @Setter private Long id;
    @Getter private Long communityId;
    @Getter private String email;
    @Getter private LocalDate birthDate;
    @Getter private String name;
    @Getter private Integer streak;
    @Getter private String commitment;
    @Getter private OffsetDateTime registeredAt;
    @Getter private Integer gemBalance;
    @Getter private Integer ecopoints;
    @Getter private LocalDate lastStreakDate;

    public User(Long id, Long communityId, String email, LocalDate birthDate, String name, Integer streak,
                String commitment, OffsetDateTime registeredAt, Integer gemBalance, Integer ecopoints,
                LocalDate lastStreakDate) {
        this.id = id;
        this.communityId = communityId;
        this.email = Objects.requireNonNull(email, "email must not be null");
        this.birthDate = birthDate;
        this.name = Objects.requireNonNull(name, "name must not be null");
        this.streak = streak == null ? 0 : streak;
        this.commitment = commitment;
        this.registeredAt = registeredAt == null ? OffsetDateTime.now() : registeredAt;
        this.gemBalance = gemBalance == null ? 0 : gemBalance;
        this.ecopoints = ecopoints == null ? 0 : ecopoints;
        this.lastStreakDate = lastStreakDate;
    }

    public void update(Long communityId, String email, LocalDate birthDate, String name, Integer streak,
                       String commitment, OffsetDateTime registeredAt, Integer gemBalance, Integer ecopoints,
                       LocalDate lastStreakDate) {
        this.communityId = communityId;
        this.email = Objects.requireNonNull(email, "email must not be null");
        this.birthDate = birthDate;
        this.name = Objects.requireNonNull(name, "name must not be null");
        this.streak = streak == null ? 0 : streak;
        this.commitment = commitment;
        this.registeredAt = registeredAt == null ? this.registeredAt : registeredAt;
        this.gemBalance = gemBalance == null ? 0 : gemBalance;
        this.ecopoints = ecopoints == null ? 0 : ecopoints;
        this.lastStreakDate = lastStreakDate;
    }

    public void updateCommitment(String commitment) {
        this.commitment = commitment;
    }

    public void updateStats(Integer gemBalance, Integer ecopoints, Integer streak, LocalDate lastStreakDate) {
        if (gemBalance != null) this.gemBalance = gemBalance;
        if (ecopoints != null) this.ecopoints = ecopoints;
        if (streak != null) this.streak = streak;
        if (lastStreakDate != null) this.lastStreakDate = lastStreakDate;
    }
}
