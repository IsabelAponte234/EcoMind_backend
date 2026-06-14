package pe.greenminds.ecomind_backend.community.domain.model.commands;

public record CreatePostCommand(
        Long communityId,
        Long userId,
        String content,
        Integer points,
        String imageUrl
) {
}
