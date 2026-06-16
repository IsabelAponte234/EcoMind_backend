package pe.greenminds.ecomind_backend.ranking.interfaces.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.greenminds.ecomind_backend.ranking.domain.model.queries.GetAllScoreEntriesQuery;
import pe.greenminds.ecomind_backend.ranking.domain.model.queries.GetScoreEntriesByUserIdQuery;
import pe.greenminds.ecomind_backend.ranking.domain.model.queries.GetScoreEntryByIdQuery;
import pe.greenminds.ecomind_backend.ranking.domain.services.ScoreEntryCommandService;
import pe.greenminds.ecomind_backend.ranking.domain.services.ScoreEntryQueryService;
import pe.greenminds.ecomind_backend.ranking.interfaces.rest.resources.CreateScoreEntryResource;
import pe.greenminds.ecomind_backend.ranking.interfaces.rest.resources.ScoreEntryResource;
import pe.greenminds.ecomind_backend.ranking.interfaces.rest.transform.CreateScoreEntryCommandFromResourceAssembler;
import pe.greenminds.ecomind_backend.ranking.interfaces.rest.transform.ScoreEntryResourceFromEntityAssembler;
import pe.greenminds.ecomind_backend.shared.interfaces.rest.resources.MessageResource;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/score-entries", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Score Entries", description = "Score Entry Management Endpoints")
public class ScoreEntriesController {

    private final ScoreEntryCommandService scoreEntryCommandService;
    private final ScoreEntryQueryService scoreEntryQueryService;

    public ScoreEntriesController(ScoreEntryCommandService scoreEntryCommandService,
                                   ScoreEntryQueryService scoreEntryQueryService) {
        this.scoreEntryCommandService = scoreEntryCommandService;
        this.scoreEntryQueryService = scoreEntryQueryService;
    }

    @PostMapping
    public ResponseEntity<ScoreEntryResource> createScoreEntry(
            @Valid @RequestBody CreateScoreEntryResource resource) {
        var createScoreEntryCommand =
                CreateScoreEntryCommandFromResourceAssembler.toCommandFromResource(resource);
        var scoreEntryId = scoreEntryCommandService.handle(createScoreEntryCommand);
        var getScoreEntryByIdQuery = new GetScoreEntryByIdQuery(scoreEntryId);
        var scoreEntry = scoreEntryQueryService.handle(getScoreEntryByIdQuery);
        if (scoreEntry.isPresent()) {
            var scoreEntryResource =
                    ScoreEntryResourceFromEntityAssembler.toResourceFromEntity(scoreEntry.get());
            return new ResponseEntity<>(scoreEntryResource, HttpStatus.CREATED);
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping
    public ResponseEntity<List<ScoreEntryResource>> getAllScoreEntries(
            @RequestParam(required = false) Long userId) {
        if (userId != null) {
            var query = new GetScoreEntriesByUserIdQuery(userId);
            var scoreEntries = scoreEntryQueryService.handle(query);
            var resources = scoreEntries.stream()
                    .map(ScoreEntryResourceFromEntityAssembler::toResourceFromEntity)
                    .toList();
            return ResponseEntity.ok(resources);
        }
        var getAllScoreEntriesQuery = new GetAllScoreEntriesQuery();
        var scoreEntries = scoreEntryQueryService.handle(getAllScoreEntriesQuery);
        var scoreEntryResources = scoreEntries.stream()
                .map(ScoreEntryResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(scoreEntryResources);
    }

    @GetMapping("/{scoreEntryId}")
    public ResponseEntity<ScoreEntryResource> getScoreEntryById(
            @PathVariable Long scoreEntryId) {
        var getScoreEntryByIdQuery = new GetScoreEntryByIdQuery(scoreEntryId);
        var scoreEntry = scoreEntryQueryService.handle(getScoreEntryByIdQuery);
        if (scoreEntry.isPresent()) {
            var scoreEntryResource =
                    ScoreEntryResourceFromEntityAssembler.toResourceFromEntity(scoreEntry.get());
            return ResponseEntity.ok(scoreEntryResource);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{scoreEntryId}")
    public ResponseEntity<MessageResource> deleteScoreEntry(
            @PathVariable Long scoreEntryId) {
        scoreEntryCommandService.handle(
                new pe.greenminds.ecomind_backend.ranking.domain.model.commands.DeleteScoreEntryCommand(scoreEntryId));
        return ResponseEntity.ok(new MessageResource(
                "ScoreEntry with id " + scoreEntryId + " deleted successfully"));
    }
}
