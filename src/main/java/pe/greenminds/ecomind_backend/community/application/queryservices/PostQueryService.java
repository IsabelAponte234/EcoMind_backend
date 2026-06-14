package pe.greenminds.ecomind_backend.community.application.queryservices;

import pe.greenminds.ecomind_backend.community.domain.model.aggregates.Post;
import pe.greenminds.ecomind_backend.community.domain.model.queries.SearchPostsQuery;

import java.util.List;

public interface PostQueryService {
    List<Post> handle(SearchPostsQuery query);
}
