package pe.greenminds.ecomind_backend.profile.interfaces.rest.transform;

import pe.greenminds.ecomind_backend.profile.domain.model.aggregates.FamilyUser;
import pe.greenminds.ecomind_backend.profile.interfaces.rest.resources.FamilyUserResource;

public class FamilyUserResourceFromEntityAssembler {
    private FamilyUserResourceFromEntityAssembler() {}

    public static FamilyUserResource toResourceFromEntity(FamilyUser familyUser) {
        return new FamilyUserResource(familyUser.getId(), familyUser.getUserId(), familyUser.getFamilyId(),
                familyUser.getFamilyRole(), ProfileDateTimeMapper.from(familyUser.getJoinedAt()));
    }
}
