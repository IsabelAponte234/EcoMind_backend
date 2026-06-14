package pe.greenminds.ecomind_backend.community.application.internal.queryservices;

import org.springframework.stereotype.Service;
import pe.greenminds.ecomind_backend.community.application.queryservices.PostQueryService;
import pe.greenminds.ecomind_backend.community.domain.model.aggregates.Post;
import pe.greenminds.ecomind_backend.community.domain.model.queries.SearchPostsQuery;
import pe.greenminds.ecomind_backend.community.domain.repositories.PostRepository;

import java.util.List;

@Service
public class PostQueryServiceImpl implements PostQueryService {
    private final PostRepository postRepository;

    public PostQueryServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public List<Post> handle(SearchPostsQuery query) {
        return postRepository.search(
                query.communityId(),
                query.userId()
        );
    }
}
