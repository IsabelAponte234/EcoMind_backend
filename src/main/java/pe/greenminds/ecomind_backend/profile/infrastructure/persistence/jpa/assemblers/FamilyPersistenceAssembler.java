package pe.greenminds.ecomind_backend.profile.infrastructure.persistence.jpa.assemblers;

import pe.greenminds.ecomind_backend.profile.domain.model.aggregates.Family;
import pe.greenminds.ecomind_backend.profile.infrastructure.persistence.jpa.entities.FamilyPersistenceEntity;

public class FamilyPersistenceAssembler {
    private FamilyPersistenceAssembler() {}

    public static Family toDomainFromPersistence(FamilyPersistenceEntity entity) {
        return new Family(entity.getId(), entity.getName(), entity.getCommitment());
    }

    public static FamilyPersistenceEntity toPersistenceFromDomain(Family family) {
        var entity = new FamilyPersistenceEntity();
        entity.setId(family.getId());
        entity.setName(family.getName());
        entity.setCommitment(family.getCommitment());
        return entity;
    }
}
