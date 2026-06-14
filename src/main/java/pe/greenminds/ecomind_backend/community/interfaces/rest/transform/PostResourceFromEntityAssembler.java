package pe.greenminds.ecomind_backend.community.interfaces.rest.transform;

import pe.greenminds.ecomind_backend.community.domain.model.aggregates.Post;
import pe.greenminds.ecomind_backend.community.interfaces.rest.resources.CreatePostResource;
import pe.greenminds.ecomind_backend.community.interfaces.rest.resources.PostResource;

public class PostResourceFromEntityAssembler {
    private PostResourceFromEntityAssembler() {}

    public static PostResource toResourceFromEntity(Post post) {
        return new PostResource(
                post.getId(),
                post.getCommunityId(),
                post.getUserId(),
                post.getContent(),
                post.getPoints(),
                post.getLikes(),
                post.getImageUrl(),
                post.getCreatedAt()
        );
    }
}
