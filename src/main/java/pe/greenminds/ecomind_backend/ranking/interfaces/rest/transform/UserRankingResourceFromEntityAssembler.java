package pe.greenminds.ecomind_backend.ranking.interfaces.rest.transform;

import pe.greenminds.ecomind_backend.ranking.infrastructure.persistence.jpa.entities.UserRankingEntity;
import pe.greenminds.ecomind_backend.ranking.interfaces.rest.resources.UserRankingResource;

public class UserRankingResourceFromEntityAssembler {
    public static UserRankingResource toResourceFromEntity(UserRankingEntity entity) {
        return new UserRankingResource(
                entity.getId(),
                entity.getUserId(),
                entity.getRankingId(),
                entity.getRankPosition(),
                entity.getScore()
        );
    }
}
