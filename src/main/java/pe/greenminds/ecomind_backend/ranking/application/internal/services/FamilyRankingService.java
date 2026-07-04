package pe.greenminds.ecomind_backend.ranking.application.internal.services;

import org.springframework.stereotype.Service;
import pe.greenminds.ecomind_backend.ranking.application.outboundservices.external.ProfileRankingExternalService;
import pe.greenminds.ecomind_backend.ranking.domain.model.valueobjects.FamilyRankingEntry;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class FamilyRankingService {

    private final ProfileRankingExternalService profileRankingExternalService;

    public FamilyRankingService(ProfileRankingExternalService profileRankingExternalService) {
        this.profileRankingExternalService = profileRankingExternalService;
    }

    public List<FamilyRankingEntry> computeFamilyRanking() {
        var families = profileRankingExternalService.fetchAllFamilies();
        var sorted = families.stream()
                .map(family -> new Object[]{
                        family.getId(),
                        family.getName(),
                        profileRankingExternalService.fetchFamilyEcopointsTotal(family.getId())
                })
                .sorted(Comparator.comparingInt(entry -> -((Integer) entry[2])))
                .toList();

        var result = new java.util.ArrayList<FamilyRankingEntry>();
        for (int i = 0; i < sorted.size(); i++) {
            var entry = sorted.get(i);
            result.add(new FamilyRankingEntry(
                    (Long) entry[0],
                    (String) entry[1],
                    (Integer) entry[2],
                    i + 1
            ));
        }
        return result;
    }

    public Optional<FamilyRankingEntry> findFamilyPosition(Long familyId) {
        return computeFamilyRanking().stream()
                .filter(entry -> entry.familyId().equals(familyId))
                .findFirst();
    }
}
