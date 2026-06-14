package pe.greenminds.ecomind_backend.community.interfaces.rest.transform;

import pe.greenminds.ecomind_backend.community.domain.model.aggregates.Community;
import pe.greenminds.ecomind_backend.community.interfaces.rest.resources.CommunityResource;

public class CommunityResourceFromEntityAssembler {
    private CommunityResourceFromEntityAssembler() {}

    public static CommunityResource toResourceFromEntity(Community community) {
        return new CommunityResource(
                community.getId(),
                community.getName(),
                community.getUserCount(),
                community.getLocation()
        );
    }
}
