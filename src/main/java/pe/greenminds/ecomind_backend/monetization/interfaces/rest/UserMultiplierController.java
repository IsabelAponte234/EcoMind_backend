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
import pe.greenminds.ecomind_backend.monetization.application.commandservices.UserMultiplierCommandService;
import pe.greenminds.ecomind_backend.monetization.application.queryservices.UserMultiplierQueryService;
import pe.greenminds.ecomind_backend.monetization.domain.model.queries.GetAllUserMultipliersQuery;
import pe.greenminds.ecomind_backend.monetization.domain.model.queries.GetUserMultiplierByIdQuery;
import pe.greenminds.ecomind_backend.monetization.domain.model.queries.GetUserMultipliersByUserIdQuery;
import pe.greenminds.ecomind_backend.monetization.interfaces.rest.resources.CreateUserMultiplierResource;
import pe.greenminds.ecomind_backend.monetization.interfaces.rest.resources.PurchaseUserMultiplierResource;
import pe.greenminds.ecomind_backend.monetization.interfaces.rest.resources.UserMultiplierResource;
import pe.greenminds.ecomind_backend.monetization.interfaces.rest.transform.CreateUserMultiplierCommandFromResourceAssembler;
import pe.greenminds.ecomind_backend.monetization.interfaces.rest.transform.PurchaseUserMultiplierCommandFromResourceAssembler;
import pe.greenminds.ecomind_backend.monetization.interfaces.rest.transform.UserMultiplierResourceFromEntityAssembler;
import pe.greenminds.ecomind_backend.shared.application.result.ApplicationError;
import pe.greenminds.ecomind_backend.shared.interfaces.rest.transform.ErrorResponseAssembler;
import pe.greenminds.ecomind_backend.shared.interfaces.rest.transform.ResponseEntityAssembler;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/user_multiplier", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "User Multipliers", description = "User multiplier activation endpoints")
public class UserMultiplierController {

    private final UserMultiplierCommandService userMultiplierCommandService;
    private final UserMultiplierQueryService userMultiplierQueryService;

    public UserMultiplierController(UserMultiplierCommandService userMultiplierCommandService, UserMultiplierQueryService userMultiplierQueryService) {
        this.userMultiplierCommandService = userMultiplierCommandService;
        this.userMultiplierQueryService = userMultiplierQueryService;
    }

    @PostMapping
    @Operation(
            summary = "Activate a multiplier for a user",
            description = "Creates a new user multiplier activation record."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "User multiplier created successfully",
                    content = @Content(schema = @Schema(implementation = UserMultiplierResource.class))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "409", description = "Conflict: user multiplier already exists")
    })
    public ResponseEntity<?> createUserMultiplier(@Valid @RequestBody CreateUserMultiplierResource resource) {
        var command = CreateUserMultiplierCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = userMultiplierCommandService.handle(command);

        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                UserMultiplierResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED
        );
    }

    @PostMapping("/purchase")
    @Operation(
            summary = "Buy a multiplier with gems",
            description = "Buys a multiplier for a user in a single atomic transaction: validates the multiplier exists, charges the user's gem balance server-side, activates it (start now, end after its duration) and records the SPEND gem movement."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Multiplier purchased successfully",
                    content = @Content(schema = @Schema(implementation = UserMultiplierResource.class))
            ),
            @ApiResponse(responseCode = "404", description = "Multiplier or user not found"),
            @ApiResponse(responseCode = "422", description = "Insufficient gem balance")
    })
    public ResponseEntity<?> purchaseUserMultiplier(@Valid @RequestBody PurchaseUserMultiplierResource resource) {
        var command = PurchaseUserMultiplierCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = userMultiplierCommandService.handle(command);

        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                UserMultiplierResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED
        );
    }

    @GetMapping
    @Operation(
            summary = "Get all user multipliers",
            description = "Retrieves all user multiplier activation records."
    )
    @ApiResponse(
            responseCode = "200",
            description = "User multipliers retrieved successfully."
    )
    public ResponseEntity<List<UserMultiplierResource>> getAllUserMultipliers() {
        var userMultipliers = userMultiplierQueryService.handle(new GetAllUserMultipliersQuery());

        var resources = userMultipliers.stream()
                .map(UserMultiplierResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }

    @GetMapping("/user/{userId}")
    @Operation(
            summary = "Get user multipliers by user ID",
            description = "Retrieves all multiplier activations belonging to a specific user."
    )
    @ApiResponse(
            responseCode = "200",
            description = "User multipliers retrieved successfully."
    )
    public ResponseEntity<List<UserMultiplierResource>> getUserMultipliersByUserId(@PathVariable Long userId) {
        var userMultipliers = userMultiplierQueryService.handle(new GetUserMultipliersByUserIdQuery(userId));

        var resources = userMultipliers.stream()
                .map(UserMultiplierResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }

    @GetMapping("/{userMultiplierId}")
    @Operation(
            summary = "Get user multiplier by ID",
            description = "Retrieves a user multiplier using its unique identifier."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "User multiplier found",
                    content = @Content(schema = @Schema(implementation = UserMultiplierResource.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User multiplier not found"
            )
    })
    public ResponseEntity<?> getUserMultiplierById(@PathVariable Long userMultiplierId) {
        var userMultiplier = userMultiplierQueryService.handle(new GetUserMultiplierByIdQuery(userMultiplierId));
        if (userMultiplier.isEmpty()) {
            var error = ApplicationError.notFound("UserMultiplier", userMultiplierId.toString());
            return ErrorResponseAssembler.toErrorResponseFromApplicationError(error);
        }
        var resource = UserMultiplierResourceFromEntityAssembler.toResourceFromEntity(userMultiplier.get());
        return ResponseEntity.ok(resource);
    }
}
