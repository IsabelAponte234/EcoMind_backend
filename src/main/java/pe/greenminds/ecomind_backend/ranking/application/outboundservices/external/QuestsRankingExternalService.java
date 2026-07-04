package pe.greenminds.ecomind_backend.ranking.application.outboundservices.external;

import org.springframework.stereotype.Service;
import pe.greenminds.ecomind_backend.quests.domain.model.valueobjects.FamilyPlanStatus;
import pe.greenminds.ecomind_backend.quests.domain.repositories.FamilyPlanRepository;

@Service
public class QuestsRankingExternalService {

    private final FamilyPlanRepository familyPlanRepository;

    public QuestsRankingExternalService(FamilyPlanRepository familyPlanRepository) {
        this.familyPlanRepository = familyPlanRepository;
    }

    public int countCompletedFamilyPlans(Long familyId) {
        return (int) familyPlanRepository.findByFamilyId(familyId).stream()
                .filter(plan -> plan.getStatus() == FamilyPlanStatus.COMPLETED)
                .count();
    }
}
