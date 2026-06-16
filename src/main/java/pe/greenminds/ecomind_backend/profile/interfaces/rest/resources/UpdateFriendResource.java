package pe.greenminds.ecomind_backend.profile.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import pe.greenminds.ecomind_backend.profile.domain.model.valueobjects.FriendStatus;

public record UpdateFriendResource(
        @JsonProperty("user_id") @NotNull Long userId,
        @JsonProperty("friend_id") @NotNull Long friendId,
        FriendStatus status
) {
}
