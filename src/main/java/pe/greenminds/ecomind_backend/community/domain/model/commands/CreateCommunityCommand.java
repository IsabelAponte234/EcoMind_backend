package pe.greenminds.ecomind_backend.community.domain.model.commands;

public record CreateCommunityCommand(
        String name,
        String location
) {
}
