package pe.greenminds.ecomind_backend.profile.domain.model.commands;

import pe.greenminds.ecomind_backend.profile.domain.model.valueobjects.FamilyRole;

import java.time.OffsetDateTime;

public record UpdateFamilyUserCommand(Long familyUserId, FamilyRole familyRole, OffsetDateTime joinedAt) {
}
