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
import pe.greenminds.ecomind_backend.monetization.application.commandservices.GemPackageCommandService;
import pe.greenminds.ecomind_backend.monetization.application.queryservices.GemPackageQueryService;
import pe.greenminds.ecomind_backend.monetization.domain.model.queries.GetAllGemPackagesQuery;
import pe.greenminds.ecomind_backend.monetization.domain.model.queries.GetGemPackageByIdQuery;
import pe.greenminds.ecomind_backend.monetization.interfaces.rest.resources.CreateGemPackageResource;
import pe.greenminds.ecomind_backend.monetization.interfaces.rest.resources.GemPackageResource;
import pe.greenminds.ecomind_backend.monetization.interfaces.rest.transform.CreateGemPackageCommandFromResourceAssembler;
import pe.greenminds.ecomind_backend.monetization.interfaces.rest.transform.GemPackageResourceFromEntityAssembler;
import pe.greenminds.ecomind_backend.shared.application.result.ApplicationError;
import pe.greenminds.ecomind_backend.shared.interfaces.rest.transform.ErrorResponseAssembler;
import pe.greenminds.ecomind_backend.shared.interfaces.rest.transform.ResponseEntityAssembler;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/gem_package", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Gem Packages", description = "Gem package catalog endpoints")
public class GemPackageController {

    private final GemPackageCommandService gemPackageCommandService;
    private final GemPackageQueryService gemPackageQueryService;

    public GemPackageController(GemPackageCommandService gemPackageCommandService, GemPackageQueryService gemPackageQueryService) {
        this.gemPackageCommandService = gemPackageCommandService;
        this.gemPackageQueryService = gemPackageQueryService;
    }

    @PostMapping
    @Operation(
            summary = "Create a new gem package",
            description = "Creates a new gem package with all necessary information."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Gem package created successfully",
                    content = @Content(schema = @Schema(implementation = GemPackageResource.class))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "409", description = "Conflict: gem package already exists")
    })
    public ResponseEntity<?> createGemPackage(@Valid @RequestBody CreateGemPackageResource resource) {
        var command = CreateGemPackageCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = gemPackageCommandService.handle(command);

        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                GemPackageResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED
        );
    }

    @GetMapping
    @Operation(
            summary = "Get all gem packages",
            description = "Retrieves all available gem packages."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Gem packages retrieved successfully."
    )
    public ResponseEntity<List<GemPackageResource>> getAllGemPackages() {
        var gemPackages = gemPackageQueryService.handle(new GetAllGemPackagesQuery());

        var resources = gemPackages.stream()
                .map(GemPackageResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }

    @GetMapping("/{gemPackageId}")
    @Operation(
            summary = "Get gem package by ID",
            description = "Retrieves a gem package using its unique identifier."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Gem package found",
                    content = @Content(schema = @Schema(implementation = GemPackageResource.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Gem package not found"
            )
    })
    public ResponseEntity<?> getGemPackageById(@PathVariable Long gemPackageId) {
        var gemPackage = gemPackageQueryService.handle(new GetGemPackageByIdQuery(gemPackageId));
        if (gemPackage.isEmpty()) {
            var error = ApplicationError.notFound("GemPackage", gemPackageId.toString());
            return ErrorResponseAssembler.toErrorResponseFromApplicationError(error);
        }
        var resource = GemPackageResourceFromEntityAssembler.toResourceFromEntity(gemPackage.get());
        return ResponseEntity.ok(resource);
    }
}
