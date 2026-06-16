package pe.greenminds.ecomind_backend.profile.interfaces.rest.transform;

import pe.greenminds.ecomind_backend.profile.domain.model.commands.UpdateFriendCommand;
import pe.greenminds.ecomind_backend.profile.interfaces.rest.resources.UpdateFriendResource;

public class UpdateFriendCommandFromResourceAssembler {
    private UpdateFriendCommandFromResourceAssembler() {}

    public static UpdateFriendCommand toCommandFromResource(Long friendId, UpdateFriendResource resource) {
        return new UpdateFriendCommand(friendId, resource.userId(), resource.friendId(), resource.status());
    }
}
