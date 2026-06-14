package pe.greenminds.ecomind_backend.monetization.interfaces.rest.transform;

import pe.greenminds.ecomind_backend.monetization.domain.model.aggregates.UserCosmetic;
import pe.greenminds.ecomind_backend.monetization.interfaces.rest.resources.UserCosmeticResource;

public class UserCosmeticResourceFromEntityAssembler {

    private UserCosmeticResourceFromEntityAssembler() {}

    public static UserCosmeticResource toResourceFromEntity(UserCosmetic userCosmetic) {
        return new UserCosmeticResource(
                userCosmetic.getId(),
                userCosmetic.getUserId(),
                userCosmetic.getCosmeticId(),
                userCosmetic.getAcquiredAt(),
                userCosmetic.getEquipped()
        );
    }
}
