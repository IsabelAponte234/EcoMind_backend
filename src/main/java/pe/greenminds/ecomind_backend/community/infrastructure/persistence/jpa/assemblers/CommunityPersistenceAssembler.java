package pe.greenminds.ecomind_backend.community.infrastructure.persistence.jpa.assemblers;

import pe.greenminds.ecomind_backend.community.domain.model.aggregates.Community;
import pe.greenminds.ecomind_backend.community.infrastructure.persistence.jpa.entities.CommunityPersistenceEntity;

public class CommunityPersistenceAssembler {
    private CommunityPersistenceAssembler() {}

    public static Community toDomainFromPersistence(CommunityPersistenceEntity entity) {
        var community = new Community(
                entity.getName(),
                entity.getUserCount(),
                entity.getLocation()
        );
        community.setId(entity.getId());
        return community;
    }

    public static CommunityPersistenceEntity toPersistenceFromDomain(Community community) {
        var entity = new CommunityPersistenceEntity();
        entity.setId(community.getId());
        entity.setName(community.getName());
        entity.setUserCount(community.getUserCount());
        entity.setLocation(community.getLocation());
        return entity;
    }
}
