package pe.greenminds.ecomind_backend.profile.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.greenminds.ecomind_backend.profile.application.commandservices.FamilyUserCommandService;
import pe.greenminds.ecomind_backend.profile.application.queryservices.FamilyUserQueryService;
import pe.greenminds.ecomind_backend.profile.domain.model.aggregates.FamilyUser;
import pe.greenminds.ecomind_backend.profile.domain.model.commands.DeleteFamilyUserCommand;
import pe.greenminds.ecomind_backend.profile.domain.model.queries.GetFamilyUsersQuery;
import pe.greenminds.ecomind_backend.profile.interfaces.rest.resources.*;
import pe.greenminds.ecomind_backend.profile.interfaces.rest.transform.*;
import pe.greenminds.ecomind_backend.shared.application.result.ApplicationError;
import pe.greenminds.ecomind_backend.shared.application.result.Result;
import pe.greenminds.ecomind_backend.shared.interfaces.rest.transform.ErrorResponseAssembler;
import pe.greenminds.ecomind_backend.shared.interfaces.rest.transform.ResponseEntityAssembler;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/family_user", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Family Users", description = "Family membership endpoints")
public class FamilyUserController {
    private final FamilyUserCommandService commandService;
    private final FamilyUserQueryService queryService;

    public FamilyUserController(FamilyUserCommandService commandService, FamilyUserQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @GetMapping
    @Operation(summary = "Get family memberships", description = "Retrieve all family memberships, or filter them by user ID or family ID.")
    public ResponseEntity<List<FamilyUserResource>> getFamilyUsers(
            @RequestParam(name = "user_id", required = false) Long userId,
            @RequestParam(name = "family_id", required = false) Long familyId) {
        return ResponseEntity.ok(queryService.handle(new GetFamilyUsersQuery(userId, familyId)).stream()
                .map(FamilyUserResourceFromEntityAssembler::toResourceFromEntity).toList());
    }

    @PostMapping
    @Operation(summary = "Add family member", description = "Create a family membership for a user.")
    public ResponseEntity<?> createFamilyUser(@Valid @RequestBody CreateFamilyUserResource resource) {
        return ResponseEntityAssembler.toResponseEntityFromResult(
                commandService.handle(CreateFamilyUserCommandFromResourceAssembler.toCommandFromResource(resource)),
                FamilyUserResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED);
    }

    @PutMapping("/{familyUserId}")
    @Operation(summary = "Update family membership", description = "Update the role or join date of a family membership.")
    public ResponseEntity<?> updateFamilyUser(@PathVariable Long familyUserId, @RequestBody UpdateFamilyUserResource resource) {
        return ResponseEntityAssembler.toResponseEntityFromResult(
                commandService.handle(UpdateFamilyUserCommandFromResourceAssembler.toCommandFromResource(familyUserId, resource)),
                FamilyUserResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.OK);
    }

    @DeleteMapping("/{familyUserId}")
    @Operation(summary = "Remove family member", description = "Delete a family membership.")
    public ResponseEntity<?> deleteFamilyUser(@PathVariable Long familyUserId) {
        var result = commandService.handle(new DeleteFamilyUserCommand(familyUserId));
        return switch (result) {
            case Result.Success<FamilyUser, ApplicationError> ignored -> ResponseEntity.noContent().build();
            case Result.Failure<FamilyUser, ApplicationError> failure -> ErrorResponseAssembler.toErrorResponseFromApplicationError(failure.error());
        };
    }
}
