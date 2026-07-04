package pe.greenminds.ecomind_backend.ranking.interfaces.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.greenminds.ecomind_backend.ranking.application.internal.services.FamilyRankingService;
import pe.greenminds.ecomind_backend.ranking.interfaces.rest.resources.FamilyRankingResource;
import pe.greenminds.ecomind_backend.ranking.interfaces.rest.transform.FamilyRankingResourceFromEntryAssembler;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/family-rankings", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Family Rankings", description = "Family Ranking Query Endpoints")
public class FamilyRankingsController {

    private final FamilyRankingService familyRankingService;

    public FamilyRankingsController(FamilyRankingService familyRankingService) {
        this.familyRankingService = familyRankingService;
    }

    @GetMapping
    public ResponseEntity<List<FamilyRankingResource>> getFamilyRanking() {
        var ranking = familyRankingService.computeFamilyRanking();
        var resources = ranking.stream()
                .map(FamilyRankingResourceFromEntryAssembler::toResourceFromEntry)
                .toList();
        return ResponseEntity.ok(resources);
    }
}
