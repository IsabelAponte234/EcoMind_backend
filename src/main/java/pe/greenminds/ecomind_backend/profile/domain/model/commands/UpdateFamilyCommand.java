package pe.greenminds.ecomind_backend.profile.domain.model.commands;

public record UpdateFamilyCommand(Long familyId, String name, String commitment) {
}
