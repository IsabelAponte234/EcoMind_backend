package pe.greenminds.ecomind_backend.profile.domain.model.commands;

import java.time.LocalDate;
import java.time.OffsetDateTime;

public record UpdateUserCommand(Long userId, Long communityId, String email, LocalDate birthDate, String name,
                                Integer streak, String commitment, OffsetDateTime registeredAt, Integer gemBalance,
                                Integer ecopoints, LocalDate lastStreakDate) {
}
