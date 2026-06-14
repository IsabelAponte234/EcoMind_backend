package pe.greenminds.ecomind_backend.community.interfaces.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.greenminds.ecomind_backend.community.application.commandservices.CommunityCommandService;
import pe.greenminds.ecomind_backend.community.application.queryservices.CommunityQueryService;
import pe.greenminds.ecomind_backend.community.domain.model.queries.SearchCommunitiesQuery;
import pe.greenminds.ecomind_backend.community.interfaces.rest.resources.CommunityResource;
import pe.greenminds.ecomind_backend.community.interfaces.rest.resources.CreateCommunityResource;
import pe.greenminds.ecomind_backend.community.interfaces.rest.transform.CommunityResourceFromEntityAssembler;
import pe.greenminds.ecomind_backend.community.interfaces.rest.transform.CreateCommunityCommandFromResourceAssembler;
import pe.greenminds.ecomind_backend.shared.interfaces.rest.transform.ResponseEntityAssembler;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/community/communities", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Communities", description = "Community management endpoints")
public class CommunityController {

    private final CommunityCommandService communityCommandService;
    private final CommunityQueryService communityQueryService;

    public CommunityController(CommunityCommandService communityCommandService, CommunityQueryService communityQueryService) {
        this.communityCommandService = communityCommandService;
        this.communityQueryService = communityQueryService;
    }

    @GetMapping
    public ResponseEntity<List<CommunityResource>> searchCommunities(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String location
    ){
        var query = new SearchCommunitiesQuery(name, location);
        var resources = communityQueryService.handle(query)
                .stream()
                .map(CommunityResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }

    @PostMapping
    public ResponseEntity<?> createCommunity(@Valid @RequestBody CreateCommunityResource resource){
        var command = CreateCommunityCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = communityCommandService.handle(command);

        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                CommunityResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED
        );
    }
}
