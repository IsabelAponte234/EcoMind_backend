package pe.greenminds.ecomind_backend.ranking.interfaces.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.greenminds.ecomind_backend.ranking.domain.model.queries.GetAchievementByIdQuery;
import pe.greenminds.ecomind_backend.ranking.domain.model.queries.GetAllAchievementsQuery;
import pe.greenminds.ecomind_backend.ranking.domain.services.AchievementCommandService;
import pe.greenminds.ecomind_backend.ranking.domain.services.AchievementQueryService;
import pe.greenminds.ecomind_backend.ranking.interfaces.rest.resources.AchievementResource;
import pe.greenminds.ecomind_backend.ranking.interfaces.rest.resources.CreateAchievementResource;
import pe.greenminds.ecomind_backend.ranking.interfaces.rest.transform.AchievementResourceFromEntityAssembler;
import pe.greenminds.ecomind_backend.ranking.interfaces.rest.transform.CreateAchievementCommandFromResourceAssembler;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/achievements", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Achievements", description = "Achievement Definition Management Endpoints")
public class AchievementsController {

    private final AchievementCommandService achievementCommandService;
    private final AchievementQueryService achievementQueryService;

    public AchievementsController(AchievementCommandService achievementCommandService,
                                   AchievementQueryService achievementQueryService) {
        this.achievementCommandService = achievementCommandService;
        this.achievementQueryService = achievementQueryService;
    }

    @PostMapping
    public ResponseEntity<AchievementResource> createAchievement(
            @Valid @RequestBody CreateAchievementResource resource) {
        var createAchievementCommand =
                CreateAchievementCommandFromResourceAssembler.toCommandFromResource(resource);
        var achievementId = achievementCommandService.handle(createAchievementCommand);
        var achievement = achievementQueryService.handle(new GetAchievementByIdQuery(achievementId));
        if (achievement.isPresent()) {
            var achievementResource =
                    AchievementResourceFromEntityAssembler.toResourceFromEntity(achievement.get());
            return new ResponseEntity<>(achievementResource, HttpStatus.CREATED);
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping
    public ResponseEntity<List<AchievementResource>> getAllAchievements() {
        var achievements = achievementQueryService.handle(new GetAllAchievementsQuery());
        var resources = achievements.stream()
                .map(AchievementResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }

    @GetMapping("/{achievementId}")
    public ResponseEntity<AchievementResource> getAchievementById(@PathVariable Long achievementId) {
        var achievement = achievementQueryService.handle(new GetAchievementByIdQuery(achievementId));
        if (achievement.isPresent()) {
            var achievementResource =
                    AchievementResourceFromEntityAssembler.toResourceFromEntity(achievement.get());
            return ResponseEntity.ok(achievementResource);
        }
        return ResponseEntity.notFound().build();
    }
}
