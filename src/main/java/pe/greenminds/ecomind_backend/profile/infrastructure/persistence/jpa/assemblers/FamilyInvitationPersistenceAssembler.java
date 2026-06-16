package pe.greenminds.ecomind_backend.profile.infrastructure.persistence.jpa.assemblers;

import pe.greenminds.ecomind_backend.profile.domain.model.aggregates.FamilyInvitation;
import pe.greenminds.ecomind_backend.profile.infrastructure.persistence.jpa.entities.FamilyInvitationPersistenceEntity;

public class FamilyInvitationPersistenceAssembler {
    private FamilyInvitationPersistenceAssembler() {}

    public static FamilyInvitation toDomainFromPersistence(FamilyInvitationPersistenceEntity entity) {
        return new FamilyInvitation(entity.getId(), entity.getFamilyId(), entity.getInviterUserId(),
                entity.getInvitedUserId(), entity.getInvitedRole(), entity.getStatus(), entity.getCreatedAt(),
                entity.getRespondedAt());
    }

    public static FamilyInvitationPersistenceEntity toPersistenceFromDomain(FamilyInvitation invitation) {
        var entity = new FamilyInvitationPersistenceEntity();
        entity.setId(invitation.getId());
        entity.setFamilyId(invitation.getFamilyId());
        entity.setInviterUserId(invitation.getInviterUserId());
        entity.setInvitedUserId(invitation.getInvitedUserId());
        entity.setInvitedRole(invitation.getInvitedRole());
        entity.setStatus(invitation.getStatus());
        entity.setCreatedAt(invitation.getCreatedAt());
        entity.setRespondedAt(invitation.getRespondedAt());
        return entity;
    }
}
