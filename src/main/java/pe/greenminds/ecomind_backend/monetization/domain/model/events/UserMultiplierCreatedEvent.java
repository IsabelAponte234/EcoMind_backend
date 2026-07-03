package pe.greenminds.ecomind_backend.monetization.domain.model.events;

import pe.greenminds.ecomind_backend.monetization.domain.model.aggregates.UserMultiplier;

import java.time.LocalDateTime;

public record UserMultiplierCreatedEvent(
        Long userMultiplierId,
        Long userId,
        Long multiplierId,
        LocalDateTime startDate,
        LocalDateTime endDate
) {
    public static UserMultiplierCreatedEvent from(UserMultiplier userMultiplier) {
        return new UserMultiplierCreatedEvent(
                userMultiplier.getId(),
                userMultiplier.getUserId(),
                userMultiplier.getMultiplierId(),
                userMultiplier.getStartDate(),
                userMultiplier.getEndDate()
        );
    }
}
