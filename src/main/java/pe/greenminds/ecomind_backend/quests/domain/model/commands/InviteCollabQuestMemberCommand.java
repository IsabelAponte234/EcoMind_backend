package pe.greenminds.ecomind_backend.quests.domain.model.commands;

public record InviteCollabQuestMemberCommand(
        Long sessionId,
        Long invitedByUserId,
        Long invitedUserId
) {
}
