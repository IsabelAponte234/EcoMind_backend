package pe.greenminds.ecomind_backend.ranking.application.internal.services;

import org.springframework.stereotype.Service;
import pe.greenminds.ecomind_backend.ranking.application.outboundservices.external.ProfileRankingExternalService;
import pe.greenminds.ecomind_backend.ranking.application.outboundservices.external.QuestsRankingExternalService;
import pe.greenminds.ecomind_backend.ranking.infrastructure.persistence.jpa.entities.AchievementEntity;
import pe.greenminds.ecomind_backend.ranking.infrastructure.persistence.jpa.entities.FamilyAchievementEntity;
import pe.greenminds.ecomind_backend.ranking.infrastructure.persistence.jpa.repositories.AchievementRepository;
import pe.greenminds.ecomind_backend.ranking.infrastructure.persistence.jpa.repositories.FamilyAchievementRepository;

import java.util.List;

@Service
public class FamilyAchievementEvaluationService {

    private final AchievementRepository achievementRepository;
    private final FamilyAchievementRepository familyAchievementRepository;
    private final ProfileRankingExternalService profileRankingExternalService;
    private final QuestsRankingExternalService questsRankingExternalService;
    private final FamilyRankingService familyRankingService;

    public FamilyAchievementEvaluationService(
            AchievementRepository achievementRepository,
            FamilyAchievementRepository familyAchievementRepository,
            ProfileRankingExternalService profileRankingExternalService,
            QuestsRankingExternalService questsRankingExternalService,
            FamilyRankingService familyRankingService
    ) {
        this.achievementRepository = achievementRepository;
        this.familyAchievementRepository = familyAchievementRepository;
        this.profileRankingExternalService = profileRankingExternalService;
        this.questsRankingExternalService = questsRankingExternalService;
        this.familyRankingService = familyRankingService;
    }

    public List<FamilyAchievementEntity> evaluateAndUnlock(Long familyId) {
        var memberUserIds = profileRankingExternalService.fetchFamilyMemberUserIds(familyId);
        var totalEcopoints = profileRankingExternalService.fetchFamilyEcopointsTotal(familyId);
        var completedCollabQuests = questsRankingExternalService.countCompletedCollabQuestsForUserIds(memberUserIds);
        var position = familyRankingService.findFamilyPosition(familyId)
                .map(entry -> entry.position())
                .orElse(Integer.MAX_VALUE);

        for (AchievementEntity achievement : achievementRepository.findAll()) {
            if (familyAchievementRepository.existsByFamilyIdAndAchievementId(familyId, achievement.getId())) {
                continue;
            }
            if (isUnlocked(achievement, totalEcopoints, completedCollabQuests, position)) {
                familyAchievementRepository.save(new FamilyAchievementEntity(familyId, achievement.getId()));
            }
        }

        return familyAchievementRepository.findByFamilyId(familyId);
    }

    private boolean isUnlocked(AchievementEntity achievement, int totalEcopoints, int completedCollabQuests, int position) {
        var threshold = achievement.getThresholdValue() == null ? 0 : achievement.getThresholdValue();
        return switch (achievement.getType()) {
            case FIRST_COLLAB_QUEST -> completedCollabQuests >= 1;
            case COLLAB_QUESTS_COUNT -> completedCollabQuests >= threshold;
            case ECOPOINTS_THRESHOLD -> totalEcopoints >= threshold;
            case WEEKLY_TOP3 -> position >= 1 && position <= 3;
        };
    }
}
