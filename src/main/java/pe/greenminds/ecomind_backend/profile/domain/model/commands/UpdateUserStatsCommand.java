package pe.greenminds.ecomind_backend.profile.domain.model.commands;

import java.time.LocalDate;

public record UpdateUserStatsCommand(Long userId, Integer gemBalance, Integer ecopoints, Integer streak,
                                     LocalDate lastStreakDate) {
}
