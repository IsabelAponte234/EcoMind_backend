package pe.greenminds.ecomind_backend.community.domain.model.queries;

public record SearchCommunitiesQuery(
        String name,
        String location
) {
}
