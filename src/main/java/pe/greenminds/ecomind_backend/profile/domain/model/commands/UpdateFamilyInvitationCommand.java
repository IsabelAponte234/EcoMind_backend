package pe.greenminds.ecomind_backend.profile.domain.model.commands;

import pe.greenminds.ecomind_backend.profile.domain.model.valueobjects.FamilyRole;
import pe.greenminds.ecomind_backend.profile.domain.model.valueobjects.InvitationStatus;

import java.time.OffsetDateTime;

public record UpdateFamilyInvitationCommand(Long invitationId, Long familyId, Long inviterUserId, Long invitedUserId,
                                            FamilyRole invitedRole, InvitationStatus status,
                                            OffsetDateTime createdAt, OffsetDateTime respondedAt) {
}
