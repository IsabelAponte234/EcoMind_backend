package pe.greenminds.ecomind_backend.profile.infrastructure.persistence.jpa.assemblers;

import pe.greenminds.ecomind_backend.profile.domain.model.aggregates.User;
import pe.greenminds.ecomind_backend.profile.infrastructure.persistence.jpa.entities.UserPersistenceEntity;

public class UserPersistenceAssembler {
    private UserPersistenceAssembler() {}

    public static User toDomainFromPersistence(UserPersistenceEntity entity) {
        return new User(entity.getId(), entity.getCommunityId(), entity.getEmail(), entity.getBirthDate(),
                entity.getName(), entity.getStreak(), entity.getCommitment(), entity.getRegisteredAt(),
                entity.getGemBalance(), entity.getEcopoints(), entity.getLastStreakDate());
    }

    public static UserPersistenceEntity toPersistenceFromDomain(User user) {
        var entity = new UserPersistenceEntity();
        entity.setId(user.getId());
        entity.setCommunityId(user.getCommunityId());
        entity.setEmail(user.getEmail());
        entity.setBirthDate(user.getBirthDate());
        entity.setName(user.getName());
        entity.setStreak(user.getStreak());
        entity.setCommitment(user.getCommitment());
        entity.setRegisteredAt(user.getRegisteredAt());
        entity.setGemBalance(user.getGemBalance());
        entity.setEcopoints(user.getEcopoints());
        entity.setLastStreakDate(user.getLastStreakDate());
        return entity;
    }
}
