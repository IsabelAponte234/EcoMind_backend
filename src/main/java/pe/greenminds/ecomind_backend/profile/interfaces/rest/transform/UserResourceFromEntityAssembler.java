package pe.greenminds.ecomind_backend.profile.interfaces.rest.transform;

import pe.greenminds.ecomind_backend.profile.domain.model.aggregates.User;
import pe.greenminds.ecomind_backend.profile.interfaces.rest.resources.UserResource;

public class UserResourceFromEntityAssembler {
    private UserResourceFromEntityAssembler() {}

    public static UserResource toResourceFromEntity(User user) {
        return new UserResource(user.getId(), user.getCommunityId(), user.getEmail(),
                ProfileDateTimeMapper.from(user.getBirthDate()), user.getName(), user.getStreak(),
                user.getCommitment(), ProfileDateTimeMapper.from(user.getRegisteredAt()), user.getGemBalance(),
                user.getEcopoints(), ProfileDateTimeMapper.from(user.getLastStreakDate()));
    }
}
