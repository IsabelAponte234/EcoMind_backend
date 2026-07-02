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
import pe.greenminds.ecomind_backend.monetization.application.commandservices.UserCosmeticCommandService;
import pe.greenminds.ecomind_backend.monetization.application.queryservices.UserCosmeticQueryService;
import pe.greenminds.ecomind_backend.monetization.domain.model.queries.GetAllUserCosmeticsQuery;
import pe.greenminds.ecomind_backend.monetization.domain.model.queries.GetUserCosmeticByIdQuery;
import pe.greenminds.ecomind_backend.monetization.domain.model.queries.GetUserCosmeticsByUserIdQuery;
import pe.greenminds.ecomind_backend.monetization.interfaces.rest.resources.CreateUserCosmeticResource;
import pe.greenminds.ecomind_backend.monetization.interfaces.rest.resources.PurchaseUserCosmeticResource;
import pe.greenminds.ecomind_backend.monetization.interfaces.rest.resources.UpdateUserCosmeticResource;
import pe.greenminds.ecomind_backend.monetization.interfaces.rest.resources.UserCosmeticResource;
import pe.greenminds.ecomind_backend.monetization.interfaces.rest.transform.CreateUserCosmeticCommandFromResourceAssembler;
import pe.greenminds.ecomind_backend.monetization.interfaces.rest.transform.PurchaseUserCosmeticCommandFromResourceAssembler;
import pe.greenminds.ecomind_backend.monetization.interfaces.rest.transform.UpdateUserCosmeticCommandFromResourceAssembler;
import pe.greenminds.ecomind_backend.monetization.interfaces.rest.transform.UserCosmeticResourceFromEntityAssembler;
import pe.greenminds.ecomind_backend.shared.application.result.ApplicationError;
import pe.greenminds.ecomind_backend.shared.interfaces.rest.transform.ErrorResponseAssembler;
import pe.greenminds.ecomind_backend.shared.interfaces.rest.transform.ResponseEntityAssembler;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/user_cosmetic", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "User Cosmetics", description = "User cosmetic management endpoints")
public class UserCosmeticController {

    private final UserCosmeticCommandService userCosmeticCommandService;
    private final UserCosmeticQueryService userCosmeticQueryService;

    public UserCosmeticController(UserCosmeticCommandService userCosmeticCommandService, UserCosmeticQueryService userCosmeticQueryService) {
        this.userCosmeticCommandService = userCosmeticCommandService;
        this.userCosmeticQueryService = userCosmeticQueryService;
    }

    @PostMapping
    @Operation(
            summary = "Create a new user cosmetic",
            description = "Creates a new user cosmetic record."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "User cosmetic created successfully",
                    content = @Content(schema = @Schema(implementation = UserCosmeticResource.class))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "409", description = "Conflict: user cosmetic already exists")
    })
    public ResponseEntity<?> createUserCosmetic(@Valid @RequestBody CreateUserCosmeticResource resource) {
        var command = CreateUserCosmeticCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = userCosmeticCommandService.handle(command);

        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                UserCosmeticResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED
        );
    }

    @PostMapping("/purchase")
    @Operation(
            summary = "Buy a cosmetic with gems",
            description = "Buys a cosmetic for a user in a single atomic transaction: validates the cosmetic exists and is not already owned, charges the user's gem balance server-side, creates the ownership record and records the SPEND gem movement."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Cosmetic purchased successfully",
                    content = @Content(schema = @Schema(implementation = UserCosmeticResource.class))
            ),
            @ApiResponse(responseCode = "404", description = "Cosmetic or user not found"),
            @ApiResponse(responseCode = "409", description = "Cosmetic already owned by this user"),
            @ApiResponse(responseCode = "422", description = "Insufficient gem balance")
    })
    public ResponseEntity<?> purchaseUserCosmetic(@Valid @RequestBody PurchaseUserCosmeticResource resource) {
        var command = PurchaseUserCosmeticCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = userCosmeticCommandService.handle(command);

        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                UserCosmeticResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED
        );
    }

    @GetMapping
    @Operation(
            summary = "Get all user cosmetics",
            description = "Retrieves all user cosmetics."
    )
    @ApiResponse(
            responseCode = "200",
            description = "User cosmetics retrieved successfully."
    )
    public ResponseEntity<List<UserCosmeticResource>> getAllUserCosmetics() {
        var userCosmetics = userCosmeticQueryService.handle(new GetAllUserCosmeticsQuery());

        var resources = userCosmetics.stream()
                .map(UserCosmeticResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }

    @GetMapping("/user/{userId}")
    @Operation(
            summary = "Get user cosmetics by user ID",
            description = "Retrieves all cosmetics owned by a specific user."
    )
    @ApiResponse(
            responseCode = "200",
            description = "User cosmetics retrieved successfully."
    )
    public ResponseEntity<List<UserCosmeticResource>> getUserCosmeticsByUserId(@PathVariable Long userId) {
        var userCosmetics = userCosmeticQueryService.handle(new GetUserCosmeticsByUserIdQuery(userId));

        var resources = userCosmetics.stream()
                .map(UserCosmeticResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }

    @GetMapping("/{userCosmeticId}")
    @Operation(
            summary = "Get user cosmetic by ID",
            description = "Retrieves a user cosmetic using its unique identifier."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "User cosmetic found",
                    content = @Content(schema = @Schema(implementation = UserCosmeticResource.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User cosmetic not found"
            )
    })
    public ResponseEntity<?> getUserCosmeticById(@PathVariable Long userCosmeticId) {
        var userCosmetic = userCosmeticQueryService.handle(new GetUserCosmeticByIdQuery(userCosmeticId));
        if (userCosmetic.isEmpty()) {
            var error = ApplicationError.notFound("UserCosmetic", userCosmeticId.toString());
            return ErrorResponseAssembler.toErrorResponseFromApplicationError(error);
        }
        var resource = UserCosmeticResourceFromEntityAssembler.toResourceFromEntity(userCosmetic.get());
        return ResponseEntity.ok(resource);
    }

    @PutMapping("/{userCosmeticId}")
    @Operation(
            summary = "Update a user cosmetic",
            description = "Updates an existing user cosmetic record."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User cosmetic updated successfully",
                    content = @Content(schema = @Schema(implementation = UserCosmeticResource.class))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "User cosmetic not found")
    })
    public ResponseEntity<?> updateUserCosmetic(@PathVariable Long userCosmeticId, @Valid @RequestBody UpdateUserCosmeticResource resource) {
        var command = UpdateUserCosmeticCommandFromResourceAssembler.toCommandFromResource(userCosmeticId, resource);
        var result = userCosmeticCommandService.handle(command);

        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                UserCosmeticResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.OK
        );
    }
}
