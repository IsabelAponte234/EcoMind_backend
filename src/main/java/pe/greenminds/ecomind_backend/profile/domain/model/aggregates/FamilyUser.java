package pe.greenminds.ecomind_backend.profile.domain.model.aggregates;

import lombok.Getter;
import lombok.Setter;
import pe.greenminds.ecomind_backend.profile.domain.model.valueobjects.FamilyRole;
import pe.greenminds.ecomind_backend.shared.domain.model.aggregates.AbstractDomainAggregateRoot;

import java.time.OffsetDateTime;
import java.util.Objects;

public class FamilyUser extends AbstractDomainAggregateRoot<FamilyUser> {
    @Getter
    @Setter
    private Long id;

    @Getter
    private Long userId;
    @Getter
    private Long familyId;
    @Getter
    private FamilyRole familyRole;
    @Getter
    private OffsetDateTime joinedAt;

    public FamilyUser(Long id, Long userId, Long familyId, FamilyRole familyRole, OffsetDateTime joinedAt) {
        this.id = id;
        this.userId = Objects.requireNonNull(userId, "userId must not be null");
        this.familyId = Objects.requireNonNull(familyId, "familyId must not be null");
        this.familyRole = familyRole == null ? FamilyRole.CHILD : familyRole;
        this.joinedAt = joinedAt == null ? OffsetDateTime.now() : joinedAt;
    }

    public void update(FamilyRole familyRole, OffsetDateTime joinedAt) {
        this.familyRole = familyRole == null ? this.familyRole : familyRole;
        this.joinedAt = joinedAt == null ? this.joinedAt : joinedAt;
    }
}
