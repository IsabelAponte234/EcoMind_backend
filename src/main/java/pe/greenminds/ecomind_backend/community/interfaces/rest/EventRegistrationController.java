package pe.greenminds.ecomind_backend.community.interfaces.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.greenminds.ecomind_backend.community.application.commandservices.EventRegistrationCommandService;
import pe.greenminds.ecomind_backend.community.application.queryservices.EventRegistrationQueryService;
import pe.greenminds.ecomind_backend.community.domain.model.commands.CancelEventRegistrationCommand;
import pe.greenminds.ecomind_backend.community.domain.model.queries.GetEventRegistrationByEventIdAndUserIdQuery;
import pe.greenminds.ecomind_backend.community.domain.model.queries.SearchEventRegistrationsQuery;
import pe.greenminds.ecomind_backend.community.domain.model.valueobjects.EventRegistrationStatus;
import pe.greenminds.ecomind_backend.community.interfaces.rest.resources.CreateEventRegistrationResource;
import pe.greenminds.ecomind_backend.community.interfaces.rest.resources.EventRegistrationResource;
import pe.greenminds.ecomind_backend.community.interfaces.rest.transform.CreateEventRegistrationCommandFromResourceAssembler;
import pe.greenminds.ecomind_backend.community.interfaces.rest.transform.EventRegistrationResourceFromEntityAssembler;
import pe.greenminds.ecomind_backend.shared.interfaces.rest.transform.ResponseEntityAssembler;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/community/events/{eventId}/registrations", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Event Registrations", description = "Community event registration endpoints")
public class EventRegistrationController {

    private final EventRegistrationCommandService eventRegistrationCommandService;
    private final EventRegistrationQueryService eventRegistrationQueryService;

    public EventRegistrationController(EventRegistrationCommandService eventRegistrationCommandService, EventRegistrationQueryService eventRegistrationQueryService) {
        this.eventRegistrationCommandService = eventRegistrationCommandService;
        this.eventRegistrationQueryService = eventRegistrationQueryService;
    }

    @GetMapping
    public ResponseEntity<List<EventRegistrationResource>> searchEventRegistrations(
            @PathVariable Long eventId,
            @RequestParam(name = "user_id", required = false) Long userId,
            @RequestParam(name = "family_id", required = false) Long familyId,
            @RequestParam(required = false) EventRegistrationStatus status
    ) {
        var query = new SearchEventRegistrationsQuery(eventId, userId, familyId, status);

        var resources = eventRegistrationQueryService.handle(query)
                .stream()
                .map(EventRegistrationResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(resources);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<EventRegistrationResource> getEventRegistrationByEventIdAndUserId(
            @PathVariable Long eventId,
            @PathVariable Long userId
    ) {
        var query = new GetEventRegistrationByEventIdAndUserIdQuery(eventId, userId);

        return eventRegistrationQueryService.handle(query)
                .map(EventRegistrationResourceFromEntityAssembler::toResourceFromEntity)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createEventRegistration(@PathVariable Long eventId, @Valid @RequestBody CreateEventRegistrationResource resource) {
        var command = CreateEventRegistrationCommandFromResourceAssembler.toCommandFromResource(eventId, resource);

        var result = eventRegistrationCommandService.handle(command);

        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                EventRegistrationResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED
        );
    }

    @PatchMapping("/{registrationId}/cancel")
    public ResponseEntity<?> cancelEventRegistration(@PathVariable Long eventId, @PathVariable Long registrationId) {
        var result = eventRegistrationCommandService.handle(
                new CancelEventRegistrationCommand(eventId, registrationId)
        );

        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                EventRegistrationResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.OK
        );
    }
}