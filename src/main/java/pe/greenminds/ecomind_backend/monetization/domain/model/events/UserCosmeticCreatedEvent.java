package pe.greenminds.ecomind_backend.monetization.domain.model.events;

import pe.greenminds.ecomind_backend.monetization.domain.model.aggregates.UserCosmetic;

import java.time.LocalDateTime;

public record UserCosmeticCreatedEvent(
        Long userCosmeticId,
        Long userId,
        Long cosmeticId,
        LocalDateTime acquiredAt,
        Boolean equipped
) {
    public static UserCosmeticCreatedEvent from(UserCosmetic userCosmetic) {
        return new UserCosmeticCreatedEvent(
                userCosmetic.getId(),
                userCosmetic.getUserId(),
                userCosmetic.getCosmeticId(),
                userCosmetic.getAcquiredAt(),
                userCosmetic.getEquipped()
        );
    }
}
