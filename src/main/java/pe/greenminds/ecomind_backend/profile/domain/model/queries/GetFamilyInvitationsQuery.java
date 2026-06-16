package pe.greenminds.ecomind_backend.profile.domain.model.queries;

import pe.greenminds.ecomind_backend.profile.domain.model.valueobjects.InvitationStatus;

public record GetFamilyInvitationsQuery(Long invitedUserId, Long inviterUserId, InvitationStatus status) {
}
