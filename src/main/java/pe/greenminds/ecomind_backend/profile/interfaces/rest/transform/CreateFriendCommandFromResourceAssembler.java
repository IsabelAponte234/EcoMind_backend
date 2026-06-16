package pe.greenminds.ecomind_backend.profile.interfaces.rest.transform;

import pe.greenminds.ecomind_backend.profile.domain.model.commands.CreateFriendCommand;
import pe.greenminds.ecomind_backend.profile.interfaces.rest.resources.CreateFriendResource;

public class CreateFriendCommandFromResourceAssembler {
    private CreateFriendCommandFromResourceAssembler() {}

    public static CreateFriendCommand toCommandFromResource(CreateFriendResource resource) {
        return new CreateFriendCommand(resource.userId(), resource.friendId(), resource.status());
    }
}
