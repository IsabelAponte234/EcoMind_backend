package pe.greenminds.ecomind_backend.quests.domain.model.commands;

public record RemoveCollabQuestMemberCommand(Long memberId, Long ownerUserId) {
}
