package pe.greenminds.ecomind_backend.profile.domain.model.queries;

import pe.greenminds.ecomind_backend.profile.domain.model.valueobjects.FriendStatus;

public record GetFriendsQuery(Long userId, FriendStatus status) {
}
