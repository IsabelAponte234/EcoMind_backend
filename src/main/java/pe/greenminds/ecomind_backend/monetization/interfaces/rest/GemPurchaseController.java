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
import pe.greenminds.ecomind_backend.monetization.application.commandservices.GemPurchaseCommandService;
import pe.greenminds.ecomind_backend.monetization.application.queryservices.GemPurchaseQueryService;
import pe.greenminds.ecomind_backend.monetization.domain.model.queries.GetAllGemPurchasesQuery;
import pe.greenminds.ecomind_backend.monetization.domain.model.queries.GetGemPurchaseByIdQuery;
import pe.greenminds.ecomind_backend.monetization.interfaces.rest.resources.CreateGemPurchaseResource;
import pe.greenminds.ecomind_backend.monetization.interfaces.rest.resources.GemPurchaseResource;
import pe.greenminds.ecomind_backend.monetization.interfaces.rest.transform.CreateGemPurchaseCommandFromResourceAssembler;
import pe.greenminds.ecomind_backend.monetization.interfaces.rest.transform.GemPurchaseResourceFromEntityAssembler;
import pe.greenminds.ecomind_backend.shared.application.result.ApplicationError;
import pe.greenminds.ecomind_backend.shared.interfaces.rest.transform.ErrorResponseAssembler;
import pe.greenminds.ecomind_backend.shared.interfaces.rest.transform.ResponseEntityAssembler;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/gem_purchase", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Gem Purchases", description = "Gem purchase management endpoints")
public class GemPurchaseController {

    private final GemPurchaseCommandService gemPurchaseCommandService;
    private final GemPurchaseQueryService gemPurchaseQueryService;

    public GemPurchaseController(GemPurchaseCommandService gemPurchaseCommandService, GemPurchaseQueryService gemPurchaseQueryService) {
        this.gemPurchaseCommandService = gemPurchaseCommandService;
        this.gemPurchaseQueryService = gemPurchaseQueryService;
    }

    @PostMapping
    @Operation(
            summary = "Create a new gem purchase",
            description = "Creates a new gem purchase record."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Gem purchase created successfully",
                    content = @Content(schema = @Schema(implementation = GemPurchaseResource.class))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "409", description = "Conflict: gem purchase already exists")
    })
    public ResponseEntity<?> createGemPurchase(@Valid @RequestBody CreateGemPurchaseResource resource) {
        var command = CreateGemPurchaseCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = gemPurchaseCommandService.handle(command);

        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                GemPurchaseResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED
        );
    }

    @GetMapping
    @Operation(
            summary = "Get all gem purchases",
            description = "Retrieves all gem purchases."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Gem purchases retrieved successfully."
    )
    public ResponseEntity<List<GemPurchaseResource>> getAllGemPurchases() {
        var gemPurchases = gemPurchaseQueryService.handle(new GetAllGemPurchasesQuery());

        var resources = gemPurchases.stream()
                .map(GemPurchaseResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }

    @GetMapping("/{gemPurchaseId}")
    @Operation(
            summary = "Get gem purchase by ID",
            description = "Retrieves a gem purchase using its unique identifier."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Gem purchase found",
                    content = @Content(schema = @Schema(implementation = GemPurchaseResource.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Gem purchase not found"
            )
    })
    public ResponseEntity<?> getGemPurchaseById(@PathVariable Long gemPurchaseId) {
        var gemPurchase = gemPurchaseQueryService.handle(new GetGemPurchaseByIdQuery(gemPurchaseId));
        if (gemPurchase.isEmpty()) {
            var error = ApplicationError.notFound("GemPurchase", gemPurchaseId.toString());
            return ErrorResponseAssembler.toErrorResponseFromApplicationError(error);
        }
        var resource = GemPurchaseResourceFromEntityAssembler.toResourceFromEntity(gemPurchase.get());
        return ResponseEntity.ok(resource);
    }
}
