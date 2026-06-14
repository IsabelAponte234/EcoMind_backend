package pe.greenminds.ecomind_backend.community.interfaces.rest.transform;

import pe.greenminds.ecomind_backend.community.domain.model.commands.CreatePostCommand;
import pe.greenminds.ecomind_backend.community.interfaces.rest.resources.CreatePostResource;

public class CreatePostCommandFromResourceAssembler {

    private CreatePostCommandFromResourceAssembler() {}

    public static CreatePostCommand toCommandFromResource(CreatePostResource resource) {
        return new CreatePostCommand(
                resource.communityId(),
                resource.userId(),
                resource.content(),
                resource.points(),
                resource.imageUrl()
        );
    }
}
