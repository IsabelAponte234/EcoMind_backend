package pe.greenminds.ecomind_backend.profile.interfaces.rest;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.greenminds.ecomind_backend.profile.application.commandservices.FamilyInvitationCommandService;
import pe.greenminds.ecomind_backend.profile.application.queryservices.FamilyInvitationQueryService;
import pe.greenminds.ecomind_backend.profile.domain.model.aggregates.FamilyInvitation;
import pe.greenminds.ecomind_backend.profile.domain.model.commands.*;
import pe.greenminds.ecomind_backend.profile.domain.model.queries.GetFamilyInvitationsQuery;
import pe.greenminds.ecomind_backend.profile.domain.model.valueobjects.InvitationStatus;
import pe.greenminds.ecomind_backend.profile.interfaces.rest.resources.*;
import pe.greenminds.ecomind_backend.profile.interfaces.rest.transform.*;
import pe.greenminds.ecomind_backend.shared.application.result.ApplicationError;
import pe.greenminds.ecomind_backend.shared.application.result.Result;
import pe.greenminds.ecomind_backend.shared.interfaces.rest.transform.ErrorResponseAssembler;
import pe.greenminds.ecomind_backend.shared.interfaces.rest.transform.ResponseEntityAssembler;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/family_invitation", produces = MediaType.APPLICATION_JSON_VALUE)
public class FamilyInvitationController {
    private final FamilyInvitationCommandService commandService;
    private final FamilyInvitationQueryService queryService;

    public FamilyInvitationController(FamilyInvitationCommandService commandService,
                                      FamilyInvitationQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @GetMapping
    public ResponseEntity<List<FamilyInvitationResource>> getInvitations(
            @RequestParam(name = "invited_user_id", required = false) Long invitedUserId,
            @RequestParam(name = "inviter_user_id", required = false) Long inviterUserId,
            @RequestParam(required = false) String status) {
        var parsedStatus = status == null ? null : InvitationStatus.from(status);
        return ResponseEntity.ok(queryService.handle(new GetFamilyInvitationsQuery(invitedUserId, inviterUserId, parsedStatus)).stream()
                .map(FamilyInvitationResourceFromEntityAssembler::toResourceFromEntity).toList());
    }

    @PostMapping
    public ResponseEntity<?> createInvitation(@Valid @RequestBody CreateFamilyInvitationResource resource) {
        return ResponseEntityAssembler.toResponseEntityFromResult(
                commandService.handle(CreateFamilyInvitationCommandFromResourceAssembler.toCommandFromResource(resource)),
                FamilyInvitationResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED);
    }

    @PutMapping("/{invitationId}")
    public ResponseEntity<?> updateInvitation(@PathVariable Long invitationId,
                                              @Valid @RequestBody UpdateFamilyInvitationResource resource) {
        return ResponseEntityAssembler.toResponseEntityFromResult(
                commandService.handle(UpdateFamilyInvitationCommandFromResourceAssembler.toCommandFromResource(invitationId, resource)),
                FamilyInvitationResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.OK);
    }

    @PatchMapping("/{invitationId}/accept")
    public ResponseEntity<?> acceptInvitation(@PathVariable Long invitationId,
                                              @RequestParam(name = "user_id", required = false) Long userId) {
        return ResponseEntityAssembler.toResponseEntityFromResult(
                commandService.handle(new AcceptFamilyInvitationCommand(invitationId, userId)),
                FamilyInvitationResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.OK);
    }

    @PatchMapping("/{invitationId}/reject")
    public ResponseEntity<?> rejectInvitation(@PathVariable Long invitationId) {
        return ResponseEntityAssembler.toResponseEntityFromResult(
                commandService.handle(new RejectFamilyInvitationCommand(invitationId)),
                FamilyInvitationResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.OK);
    }

    @DeleteMapping("/{invitationId}")
    public ResponseEntity<?> deleteInvitation(@PathVariable Long invitationId) {
        var result = commandService.handle(new DeleteFamilyInvitationCommand(invitationId));
        return switch (result) {
            case Result.Success<FamilyInvitation, ApplicationError> ignored -> ResponseEntity.noContent().build();
            case Result.Failure<FamilyInvitation, ApplicationError> failure -> ErrorResponseAssembler.toErrorResponseFromApplicationError(failure.error());
        };
    }
}
