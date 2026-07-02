package pe.greenminds.ecomind_backend.quests.domain.model.commands;

public record DeletePendingCollabQuestSessionCommand(Long sessionId, Long ownerUserId) {
}
