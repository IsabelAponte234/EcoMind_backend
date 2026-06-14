package pe.greenminds.ecomind_backend.ranking.interfaces.rest.transform;

import pe.greenminds.ecomind_backend.ranking.infrastructure.persistence.jpa.entities.RankingEntity;
import pe.greenminds.ecomind_backend.ranking.interfaces.rest.resources.RankingResource;

import java.text.SimpleDateFormat;

public class RankingResourceFromEntityAssembler {
    public static RankingResource toResourceFromEntity(RankingEntity entity) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        return new RankingResource(
                entity.getId(),
                entity.getName(),
                entity.getType().name(),
                sdf.format(entity.getStartDate()),
                entity.getEndDate() != null ? sdf.format(entity.getEndDate()) : null,
                entity.isStatus()
        );
    }
}
