package pe.greenminds.ecomind_backend.profile.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import pe.greenminds.ecomind_backend.profile.domain.model.valueobjects.FriendStatus;

public record FriendResource(
        Long id,
        @JsonProperty("user_id") Long userId,
        @JsonProperty("friend_id") Long friendId,
        FriendStatus status
) {
}
