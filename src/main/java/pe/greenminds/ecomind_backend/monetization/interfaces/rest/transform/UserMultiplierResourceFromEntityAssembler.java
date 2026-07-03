package pe.greenminds.ecomind_backend.monetization.interfaces.rest.transform;

import pe.greenminds.ecomind_backend.monetization.domain.model.aggregates.UserMultiplier;
import pe.greenminds.ecomind_backend.monetization.interfaces.rest.resources.UserMultiplierResource;

public class UserMultiplierResourceFromEntityAssembler {

    private UserMultiplierResourceFromEntityAssembler() {}

    public static UserMultiplierResource toResourceFromEntity(UserMultiplier userMultiplier) {
        return new UserMultiplierResource(
                userMultiplier.getId(),
                userMultiplier.getUserId(),
                userMultiplier.getMultiplierId(),
                userMultiplier.getStartDate(),
                userMultiplier.getEndDate()
        );
    }
}
