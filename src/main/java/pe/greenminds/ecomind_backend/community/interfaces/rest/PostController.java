package pe.greenminds.ecomind_backend.community.interfaces.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.greenminds.ecomind_backend.community.application.commandservices.PostCommandService;
import pe.greenminds.ecomind_backend.community.application.queryservices.PostQueryService;
import pe.greenminds.ecomind_backend.community.domain.model.commands.DeletePostCommand;
import pe.greenminds.ecomind_backend.community.domain.model.queries.SearchPostsQuery;
import pe.greenminds.ecomind_backend.community.interfaces.rest.resources.CreatePostResource;
import pe.greenminds.ecomind_backend.community.interfaces.rest.resources.PostResource;
import pe.greenminds.ecomind_backend.community.interfaces.rest.transform.CreatePostCommandFromResourceAssembler;
import pe.greenminds.ecomind_backend.community.interfaces.rest.transform.PostResourceFromEntityAssembler;
import pe.greenminds.ecomind_backend.shared.application.result.ApplicationError;
import pe.greenminds.ecomind_backend.shared.application.result.Result;
import pe.greenminds.ecomind_backend.shared.interfaces.rest.transform.ErrorResponseAssembler;
import pe.greenminds.ecomind_backend.shared.interfaces.rest.transform.ResponseEntityAssembler;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/community/posts", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Posts", description = "Community post management endpoints")
public class PostController {

    private final PostCommandService postCommandService;
    private final PostQueryService postQueryService;

    public PostController(PostCommandService postCommandService, PostQueryService postQueryService) {
        this.postCommandService = postCommandService;
        this.postQueryService = postQueryService;
    }

    @GetMapping
    public ResponseEntity<List<PostResource>> searchPosts(
            @RequestParam(name = "community_id", required = false) Long communityId,
            @RequestParam(name = "user_id", required = false) Long userId
    ) {
        var query = new SearchPostsQuery(communityId, userId);

        var resources = postQueryService.handle(query)
                .stream()
                .map(PostResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(resources);
    }

    @PostMapping
    public ResponseEntity<?> createPost(@Valid @RequestBody CreatePostResource resource) {
        var command = CreatePostCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = postCommandService.handle(command);

        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                PostResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id) {
        var result = postCommandService.handle(new DeletePostCommand(id));

        return switch (result) {
            case Result.Success<Void, ApplicationError> ignored ->
                    ResponseEntity.noContent().build();

            case Result.Failure<Void, ApplicationError> failure ->
                    ErrorResponseAssembler.toErrorResponseFromApplicationError(failure.error());
        };
    }
}
