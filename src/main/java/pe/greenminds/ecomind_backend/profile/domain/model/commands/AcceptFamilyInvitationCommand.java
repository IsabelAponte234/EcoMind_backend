package pe.greenminds.ecomind_backend.profile.domain.model.commands;

public record AcceptFamilyInvitationCommand(Long invitationId, Long acceptedByUserId) {
}
