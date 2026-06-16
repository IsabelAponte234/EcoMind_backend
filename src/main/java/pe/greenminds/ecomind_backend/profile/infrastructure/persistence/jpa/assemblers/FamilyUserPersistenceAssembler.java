package pe.greenminds.ecomind_backend.profile.infrastructure.persistence.jpa.assemblers;

import pe.greenminds.ecomind_backend.profile.domain.model.aggregates.FamilyUser;
import pe.greenminds.ecomind_backend.profile.infrastructure.persistence.jpa.entities.FamilyUserPersistenceEntity;

public class FamilyUserPersistenceAssembler {
    private FamilyUserPersistenceAssembler() {}

    public static FamilyUser toDomainFromPersistence(FamilyUserPersistenceEntity entity) {
        return new FamilyUser(entity.getId(), entity.getUserId(), entity.getFamilyId(), entity.getFamilyRole(),
                entity.getJoinedAt());
    }

    public static FamilyUserPersistenceEntity toPersistenceFromDomain(FamilyUser familyUser) {
        var entity = new FamilyUserPersistenceEntity();
        entity.setId(familyUser.getId());
        entity.setUserId(familyUser.getUserId());
        entity.setFamilyId(familyUser.getFamilyId());
        entity.setFamilyRole(familyUser.getFamilyRole());
        entity.setJoinedAt(familyUser.getJoinedAt());
        return entity;
    }
}
