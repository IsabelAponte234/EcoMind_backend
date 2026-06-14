package pe.greenminds.ecomind_backend.community.domain.model.queries;

public record SearchPostsQuery(
        Long communityId,
        Long userId
) {
}