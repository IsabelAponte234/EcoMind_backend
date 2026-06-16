package pe.greenminds.ecomind_backend.profile.domain.model.commands;

import pe.greenminds.ecomind_backend.profile.domain.model.valueobjects.FamilyRole;

import java.time.OffsetDateTime;

public record CreateFamilyUserCommand(Long userId, Long familyId, FamilyRole familyRole, OffsetDateTime joinedAt) {
}
