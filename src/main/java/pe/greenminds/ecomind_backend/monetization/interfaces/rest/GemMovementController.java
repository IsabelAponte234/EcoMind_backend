package pe.greenminds.ecomind_backend.monetization.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.greenminds.ecomind_backend.monetization.application.commandservices.GemMovementCommandService;
import pe.greenminds.ecomind_backend.monetization.application.queryservices.GemMovementQueryService;
import pe.greenminds.ecomind_backend.monetization.domain.model.queries.GetAllGemMovementsQuery;
import pe.greenminds.ecomind_backend.monetization.domain.model.queries.GetGemMovementByIdQuery;
import pe.greenminds.ecomind_backend.monetization.interfaces.rest.resources.CreateGemMovementResource;
import pe.greenminds.ecomind_backend.monetization.interfaces.rest.resources.GemMovementResource;
import pe.greenminds.ecomind_backend.monetization.interfaces.rest.transform.CreateGemMovementCommandFromResourceAssembler;
import pe.greenminds.ecomind_backend.monetization.interfaces.rest.transform.GemMovementResourceFromEntityAssembler;
import pe.greenminds.ecomind_backend.shared.application.result.ApplicationError;
import pe.greenminds.ecomind_backend.shared.interfaces.rest.transform.ErrorResponseAssembler;
import pe.greenminds.ecomind_backend.shared.interfaces.rest.transform.ResponseEntityAssembler;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/gem_movement", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Gem Movements", description = "Gem movement management endpoints")
public class GemMovementController {

    private final GemMovementCommandService gemMovementCommandService;
    private final GemMovementQueryService gemMovementQueryService;

    public GemMovementController(GemMovementCommandService gemMovementCommandService, GemMovementQueryService gemMovementQueryService) {
        this.gemMovementCommandService = gemMovementCommandService;
        this.gemMovementQueryService = gemMovementQueryService;
    }

    @PostMapping
    @Operation(
            summary = "Create a new gem movement",
            description = "Creates a new gem movement record."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Gem movement created successfully",
                    content = @Content(schema = @Schema(implementation = GemMovementResource.class))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "409", description = "Conflict: gem movement already exists")
    })
    public ResponseEntity<?> createGemMovement(@Valid @RequestBody CreateGemMovementResource resource) {
        var command = CreateGemMovementCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = gemMovementCommandService.handle(command);

        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                GemMovementResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED
        );
    }

    @GetMapping
    @Operation(
            summary = "Get all gem movements",
            description = "Retrieves all gem movements."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Gem movements retrieved successfully."
    )
    public ResponseEntity<List<GemMovementResource>> getAllGemMovements() {
        var gemMovements = gemMovementQueryService.handle(new GetAllGemMovementsQuery());

        var resources = gemMovements.stream()
                .map(GemMovementResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }

    @GetMapping("/{gemMovementId}")
    @Operation(
            summary = "Get gem movement by ID",
            description = "Retrieves a gem movement using its unique identifier."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Gem movement found",
                    content = @Content(schema = @Schema(implementation = GemMovementResource.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Gem movement not found"
            )
    })
    public ResponseEntity<?> getGemMovementById(@PathVariable Long gemMovementId) {
        var gemMovement = gemMovementQueryService.handle(new GetGemMovementByIdQuery(gemMovementId));
        if (gemMovement.isEmpty()) {
            var error = ApplicationError.notFound("GemMovement", gemMovementId.toString());
            return ErrorResponseAssembler.toErrorResponseFromApplicationError(error);
        }
        var resource = GemMovementResourceFromEntityAssembler.toResourceFromEntity(gemMovement.get());
        return ResponseEntity.ok(resource);
    }
}
