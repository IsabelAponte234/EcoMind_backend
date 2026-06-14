package pe.greenminds.ecomind_backend.monetization.interfaces.rest.transform;

import pe.greenminds.ecomind_backend.monetization.domain.model.aggregates.GemMovement;
import pe.greenminds.ecomind_backend.monetization.interfaces.rest.resources.GemMovementResource;

public class GemMovementResourceFromEntityAssembler {

    private GemMovementResourceFromEntityAssembler() {}

    public static GemMovementResource toResourceFromEntity(GemMovement gemMovement) {
        return new GemMovementResource(
                gemMovement.getId(),
                gemMovement.getUserId(),
                gemMovement.getType().name().toLowerCase(),
                gemMovement.getAmount(),
                gemMovement.getOrigin().name().toLowerCase(),
                gemMovement.getOriginId()
        );
    }
}
