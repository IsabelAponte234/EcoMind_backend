package pe.greenminds.ecomind_backend.profile.domain.model.aggregates;

import lombok.Getter;
import lombok.Setter;
import pe.greenminds.ecomind_backend.profile.domain.model.valueobjects.FamilyRole;
import pe.greenminds.ecomind_backend.profile.domain.model.valueobjects.InvitationStatus;
import pe.greenminds.ecomind_backend.shared.domain.model.aggregates.AbstractDomainAggregateRoot;

import java.time.OffsetDateTime;
import java.util.Objects;

public class FamilyInvitation extends AbstractDomainAggregateRoot<FamilyInvitation> {
    @Getter
    @Setter
    private Long id;

    @Getter
    private Long familyId;
    @Getter
    private Long inviterUserId;
    @Getter
    private Long invitedUserId;
    @Getter
    private FamilyRole invitedRole;
    @Getter
    private InvitationStatus status;
    @Getter
    private OffsetDateTime createdAt;
    @Getter
    private OffsetDateTime respondedAt;

    public FamilyInvitation(Long id, Long familyId, Long inviterUserId, Long invitedUserId, FamilyRole invitedRole,
                            InvitationStatus status, OffsetDateTime createdAt, OffsetDateTime respondedAt) {
        this.id = id;
        this.familyId = Objects.requireNonNull(familyId, "familyId must not be null");
        this.inviterUserId = Objects.requireNonNull(inviterUserId, "inviterUserId must not be null");
        this.invitedUserId = Objects.requireNonNull(invitedUserId, "invitedUserId must not be null");
        this.invitedRole = invitedRole == null ? FamilyRole.CHILD : invitedRole;
        this.status = status == null ? InvitationStatus.PENDING : status;
        this.createdAt = createdAt == null ? OffsetDateTime.now() : createdAt;
        this.respondedAt = respondedAt;
    }

    public void update(Long familyId, Long inviterUserId, Long invitedUserId, FamilyRole invitedRole,
                       InvitationStatus status, OffsetDateTime createdAt, OffsetDateTime respondedAt) {
        this.familyId = Objects.requireNonNull(familyId, "familyId must not be null");
        this.inviterUserId = Objects.requireNonNull(inviterUserId, "inviterUserId must not be null");
        this.invitedUserId = Objects.requireNonNull(invitedUserId, "invitedUserId must not be null");
        this.invitedRole = invitedRole == null ? this.invitedRole : invitedRole;
        this.status = status == null ? this.status : status;
        this.createdAt = createdAt == null ? this.createdAt : createdAt;
        this.respondedAt = respondedAt;
    }

    public void accept() {
        this.status = InvitationStatus.ACCEPTED;
        this.respondedAt = OffsetDateTime.now();
    }

    public void reject() {
        this.status = InvitationStatus.REJECTED;
        this.respondedAt = OffsetDateTime.now();
    }
}
