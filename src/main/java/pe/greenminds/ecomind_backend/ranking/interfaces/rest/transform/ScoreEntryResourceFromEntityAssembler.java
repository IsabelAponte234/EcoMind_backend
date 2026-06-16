package pe.greenminds.ecomind_backend.ranking.interfaces.rest.transform;

import pe.greenminds.ecomind_backend.ranking.infrastructure.persistence.jpa.entities.ScoreEntryEntity;
import pe.greenminds.ecomind_backend.ranking.interfaces.rest.resources.ScoreEntryResource;

import java.text.SimpleDateFormat;

public class ScoreEntryResourceFromEntityAssembler {
    public static ScoreEntryResource toResourceFromEntity(ScoreEntryEntity entity) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        return new ScoreEntryResource(
                entity.getId(),
                entity.getUserId(),
                entity.getScore(),
                entity.getEntryType(),
                entity.getDescription(),
                sdf.format(entity.getEntryDate())
        );
    }
}
