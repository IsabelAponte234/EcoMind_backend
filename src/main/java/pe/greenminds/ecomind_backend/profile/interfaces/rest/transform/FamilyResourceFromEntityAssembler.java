package pe.greenminds.ecomind_backend.profile.interfaces.rest.transform;

import pe.greenminds.ecomind_backend.profile.domain.model.aggregates.Family;
import pe.greenminds.ecomind_backend.profile.interfaces.rest.resources.FamilyResource;

public class FamilyResourceFromEntityAssembler {
    private FamilyResourceFromEntityAssembler() {}

    public static FamilyResource toResourceFromEntity(Family family) {
        return new FamilyResource(family.getId(), family.getName(), family.getCommitment());
    }
}
