package pe.greenminds.ecomind_backend.ranking.interfaces.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.greenminds.ecomind_backend.ranking.application.internal.services.FamilyAchievementEvaluationService;
import pe.greenminds.ecomind_backend.ranking.infrastructure.persistence.jpa.repositories.AchievementRepository;
import pe.greenminds.ecomind_backend.ranking.interfaces.rest.resources.FamilyAchievementResource;
import pe.greenminds.ecomind_backend.ranking.interfaces.rest.transform.FamilyAchievementResourceFromEntityAssembler;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/family-achievements", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Family Achievements", description = "Family Achievement Query Endpoints")
public class FamilyAchievementsController {

    private final FamilyAchievementEvaluationService familyAchievementEvaluationService;
    private final AchievementRepository achievementRepository;

    public FamilyAchievementsController(
            FamilyAchievementEvaluationService familyAchievementEvaluationService,
            AchievementRepository achievementRepository
    ) {
        this.familyAchievementEvaluationService = familyAchievementEvaluationService;
        this.achievementRepository = achievementRepository;
    }

    @GetMapping("/{familyId}")
    public ResponseEntity<List<FamilyAchievementResource>> getFamilyAchievements(@PathVariable Long familyId) {
        var familyAchievements = familyAchievementEvaluationService.evaluateAndUnlock(familyId);
        var resources = familyAchievements.stream()
                .map(entity -> FamilyAchievementResourceFromEntityAssembler.toResourceFromEntity(
                        entity,
                        achievementRepository.findById(entity.getAchievementId()).orElse(null)
                ))
                .toList();
        return ResponseEntity.ok(resources);
    }
}
