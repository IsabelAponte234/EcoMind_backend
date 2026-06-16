package pe.greenminds.ecomind_backend.profile.domain.model.commands;

import pe.greenminds.ecomind_backend.profile.domain.model.valueobjects.FriendStatus;

public record UpdateFriendCommand(Long id, Long userId, Long friendId, FriendStatus status) {
}
