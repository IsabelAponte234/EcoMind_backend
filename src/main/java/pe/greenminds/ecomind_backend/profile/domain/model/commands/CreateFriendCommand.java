package pe.greenminds.ecomind_backend.profile.domain.model.commands;

import pe.greenminds.ecomind_backend.profile.domain.model.valueobjects.FriendStatus;

public record CreateFriendCommand(Long userId, Long friendId, FriendStatus status) {
}
