package pe.greenminds.ecomind_backend.ranking.interfaces.rest.transform;

import pe.greenminds.ecomind_backend.ranking.domain.model.valueobjects.FamilyRankingEntry;
import pe.greenminds.ecomind_backend.ranking.interfaces.rest.resources.FamilyRankingResource;

public class FamilyRankingResourceFromEntryAssembler {
    public static FamilyRankingResource toResourceFromEntry(FamilyRankingEntry entry) {
        return new FamilyRankingResource(
                entry.familyId(),
                entry.familyName(),
                entry.totalEcopoints(),
                entry.position()
        );
    }
}
