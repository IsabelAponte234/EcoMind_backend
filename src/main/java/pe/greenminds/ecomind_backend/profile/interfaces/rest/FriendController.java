package pe.greenminds.ecomind_backend.profile.interfaces.rest;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.greenminds.ecomind_backend.profile.application.commandservices.FriendCommandService;
import pe.greenminds.ecomind_backend.profile.application.queryservices.FriendQueryService;
import pe.greenminds.ecomind_backend.profile.domain.model.aggregates.Friend;
import pe.greenminds.ecomind_backend.profile.domain.model.commands.*;
import pe.greenminds.ecomind_backend.profile.domain.model.queries.GetFriendsQuery;
import pe.greenminds.ecomind_backend.profile.domain.model.valueobjects.FriendStatus;
import pe.greenminds.ecomind_backend.profile.interfaces.rest.resources.*;
import pe.greenminds.ecomind_backend.profile.interfaces.rest.transform.*;
import pe.greenminds.ecomind_backend.shared.application.result.ApplicationError;
import pe.greenminds.ecomind_backend.shared.application.result.Result;
import pe.greenminds.ecomind_backend.shared.interfaces.rest.transform.ErrorResponseAssembler;
import pe.greenminds.ecomind_backend.shared.interfaces.rest.transform.ResponseEntityAssembler;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/friend", produces = MediaType.APPLICATION_JSON_VALUE)
public class FriendController {
    private final FriendCommandService commandService;
    private final FriendQueryService queryService;

    public FriendController(FriendCommandService commandService, FriendQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @GetMapping
    public ResponseEntity<List<FriendResource>> getFriends(
            @RequestParam(name = "user_id", required = false) Long userId,
            @RequestParam(required = false) String status) {
        var parsedStatus = status == null ? null : FriendStatus.from(status);
        return ResponseEntity.ok(queryService.handle(new GetFriendsQuery(userId, parsedStatus)).stream()
                .map(FriendResourceFromEntityAssembler::toResourceFromEntity).toList());
    }

    @PostMapping
    public ResponseEntity<?> createFriend(@Valid @RequestBody CreateFriendResource resource) {
        return ResponseEntityAssembler.toResponseEntityFromResult(
                commandService.handle(CreateFriendCommandFromResourceAssembler.toCommandFromResource(resource)),
                FriendResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED);
    }

    @PutMapping("/{friendId}")
    public ResponseEntity<?> updateFriend(@PathVariable Long friendId, @Valid @RequestBody UpdateFriendResource resource) {
        return ResponseEntityAssembler.toResponseEntityFromResult(
                commandService.handle(UpdateFriendCommandFromResourceAssembler.toCommandFromResource(friendId, resource)),
                FriendResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.OK);
    }

    @PatchMapping("/{friendId}/accept")
    public ResponseEntity<?> acceptFriend(@PathVariable Long friendId) {
        return ResponseEntityAssembler.toResponseEntityFromResult(
                commandService.handle(new AcceptFriendCommand(friendId)),
                FriendResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.OK);
    }

    @PatchMapping("/{friendId}/reject")
    public ResponseEntity<?> rejectFriend(@PathVariable Long friendId) {
        return ResponseEntityAssembler.toResponseEntityFromResult(
                commandService.handle(new RejectFriendCommand(friendId)),
                FriendResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.OK);
    }

    @DeleteMapping("/{friendId}")
    public ResponseEntity<?> deleteFriend(@PathVariable Long friendId) {
        var result = commandService.handle(new DeleteFriendCommand(friendId));
        return switch (result) {
            case Result.Success<Friend, ApplicationError> ignored -> ResponseEntity.noContent().build();
            case Result.Failure<Friend, ApplicationError> failure -> ErrorResponseAssembler.toErrorResponseFromApplicationError(failure.error());
        };
    }
}
