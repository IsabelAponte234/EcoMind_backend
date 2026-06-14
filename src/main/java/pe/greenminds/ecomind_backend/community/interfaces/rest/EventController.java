package pe.greenminds.ecomind_backend.community.interfaces.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.greenminds.ecomind_backend.community.application.commandservices.EventCommandService;
import pe.greenminds.ecomind_backend.community.application.queryservices.EventQueryService;
import pe.greenminds.ecomind_backend.community.domain.model.commands.DeleteEventCommand;
import pe.greenminds.ecomind_backend.community.domain.model.queries.SearchEventsQuery;
import pe.greenminds.ecomind_backend.community.interfaces.rest.resources.CreateEventResource;
import pe.greenminds.ecomind_backend.community.interfaces.rest.resources.EventResource;
import pe.greenminds.ecomind_backend.community.interfaces.rest.transform.CreateEventCommandFromResourceAssembler;
import pe.greenminds.ecomind_backend.community.interfaces.rest.transform.EventResourceFromEntityAssembler;
import pe.greenminds.ecomind_backend.shared.application.result.ApplicationError;
import pe.greenminds.ecomind_backend.shared.application.result.Result;
import pe.greenminds.ecomind_backend.shared.interfaces.rest.transform.ErrorResponseAssembler;
import pe.greenminds.ecomind_backend.shared.interfaces.rest.transform.ResponseEntityAssembler;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/community/events", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Events", description = "Community event management endpoints")
public class EventController {

    private final EventCommandService eventCommandService;
    private final EventQueryService eventQueryService;

    public EventController(EventCommandService eventCommandService, EventQueryService eventQueryService) {
        this.eventCommandService = eventCommandService;
        this.eventQueryService = eventQueryService;
    }

    @GetMapping
    public ResponseEntity<List<EventResource>> searchEvents(
            @RequestParam(name = "community_id", required = false) Long communityId,
            @RequestParam(name = "author_id", required = false) Long authorId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate date,
            @RequestParam(required = false) String location
    ) {
        var query = new SearchEventsQuery(communityId, authorId, name, date, location);

        var resources = eventQueryService.handle(query)
                .stream()
                .map(EventResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(resources);
    }

    @PostMapping
    public ResponseEntity<?> createEvent(@Valid @RequestBody CreateEventResource resource) {
        var command = CreateEventCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = eventCommandService.handle(command);

        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                EventResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEvent(@PathVariable Long id) {
        var result = eventCommandService.handle(new DeleteEventCommand(id));

        return switch (result) {
            case Result.Success<Void, ApplicationError> ignored ->
                    ResponseEntity.noContent().build();

            case Result.Failure<Void, ApplicationError> failure ->
                    ErrorResponseAssembler.toErrorResponseFromApplicationError(failure.error());
        };
    }
}