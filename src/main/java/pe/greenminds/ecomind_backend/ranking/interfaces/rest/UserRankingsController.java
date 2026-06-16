package pe.greenminds.ecomind_backend.ranking.interfaces.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.greenminds.ecomind_backend.ranking.domain.model.queries.GetAllUserRankingsQuery;
import pe.greenminds.ecomind_backend.ranking.domain.model.queries.GetUserRankingByIdQuery;
import pe.greenminds.ecomind_backend.ranking.domain.model.queries.GetUserRankingsByRankingIdQuery;
import pe.greenminds.ecomind_backend.ranking.domain.services.UserRankingCommandService;
import pe.greenminds.ecomind_backend.ranking.domain.services.UserRankingQueryService;
import pe.greenminds.ecomind_backend.ranking.interfaces.rest.resources.CreateUserRankingResource;
import pe.greenminds.ecomind_backend.ranking.interfaces.rest.resources.UpdateUserRankingResource;
import pe.greenminds.ecomind_backend.ranking.interfaces.rest.resources.UserRankingResource;
import pe.greenminds.ecomind_backend.ranking.interfaces.rest.transform.CreateUserRankingCommandFromResourceAssembler;
import pe.greenminds.ecomind_backend.ranking.interfaces.rest.transform.UpdateUserRankingCommandFromResourceAssembler;
import pe.greenminds.ecomind_backend.ranking.interfaces.rest.transform.UserRankingResourceFromEntityAssembler;
import pe.greenminds.ecomind_backend.shared.interfaces.rest.resources.MessageResource;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/user-rankings", produces = APPLICATION_JSON_VALUE)
@Tag(name = "User Rankings", description = "User Ranking Management Endpoints")
public class UserRankingsController {

    private final UserRankingCommandService userRankingCommandService;
    private final UserRankingQueryService userRankingQueryService;

    public UserRankingsController(UserRankingCommandService userRankingCommandService,
                                   UserRankingQueryService userRankingQueryService) {
        this.userRankingCommandService = userRankingCommandService;
        this.userRankingQueryService = userRankingQueryService;
    }

    @PostMapping
    public ResponseEntity<UserRankingResource> createUserRanking(
            @Valid @RequestBody CreateUserRankingResource resource) {
        var createUserRankingCommand =
                CreateUserRankingCommandFromResourceAssembler.toCommandFromResource(resource);
        var userRankingId = userRankingCommandService.handle(createUserRankingCommand);
        var getUserRankingByIdQuery = new GetUserRankingByIdQuery(userRankingId);
        var userRanking = userRankingQueryService.handle(getUserRankingByIdQuery);
        if (userRanking.isPresent()) {
            var userRankingResource =
                    UserRankingResourceFromEntityAssembler.toResourceFromEntity(userRanking.get());
            return new ResponseEntity<>(userRankingResource, HttpStatus.CREATED);
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping
    public ResponseEntity<List<UserRankingResource>> getAllUserRankings(
            @RequestParam(required = false) Long rankingId) {
        if (rankingId != null) {
            var query = new GetUserRankingsByRankingIdQuery(rankingId);
            var userRankings = userRankingQueryService.handle(query);
            var resources = userRankings.stream()
                    .map(UserRankingResourceFromEntityAssembler::toResourceFromEntity)
                    .toList();
            return ResponseEntity.ok(resources);
        }
        var getAllUserRankingsQuery = new GetAllUserRankingsQuery();
        var userRankings = userRankingQueryService.handle(getAllUserRankingsQuery);
        var userRankingResources = userRankings.stream()
                .map(UserRankingResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(userRankingResources);
    }

    @GetMapping("/{userRankingId}")
    public ResponseEntity<UserRankingResource> getUserRankingById(
            @PathVariable Long userRankingId) {
        var getUserRankingByIdQuery = new GetUserRankingByIdQuery(userRankingId);
        var userRanking = userRankingQueryService.handle(getUserRankingByIdQuery);
        if (userRanking.isPresent()) {
            var userRankingResource =
                    UserRankingResourceFromEntityAssembler.toResourceFromEntity(userRanking.get());
            return ResponseEntity.ok(userRankingResource);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{userRankingId}")
    public ResponseEntity<UserRankingResource> updateUserRanking(
            @PathVariable Long userRankingId,
            @Valid @RequestBody UpdateUserRankingResource resource) {
        var updateUserRankingCommand =
                UpdateUserRankingCommandFromResourceAssembler.toCommandFromResource(userRankingId, resource);
        var userRanking = userRankingCommandService.handle(updateUserRankingCommand);
        if (userRanking.isPresent()) {
            var userRankingResource =
                    UserRankingResourceFromEntityAssembler.toResourceFromEntity(userRanking.get());
            return ResponseEntity.ok(userRankingResource);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{userRankingId}")
    public ResponseEntity<MessageResource> deleteUserRanking(
            @PathVariable Long userRankingId) {
        userRankingCommandService.handle(
                new pe.greenminds.ecomind_backend.ranking.domain.model.commands.DeleteUserRankingCommand(userRankingId));
        return ResponseEntity.ok(new MessageResource(
                "UserRanking with id " + userRankingId + " deleted successfully"));
    }
}
