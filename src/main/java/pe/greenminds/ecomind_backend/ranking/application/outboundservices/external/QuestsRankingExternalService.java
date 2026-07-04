package pe.greenminds.ecomind_backend.ranking.application.outboundservices.external;

import org.springframework.stereotype.Service;
import pe.greenminds.ecomind_backend.quests.domain.model.valueobjects.CollabMemberStatus;
import pe.greenminds.ecomind_backend.quests.domain.model.valueobjects.CollabQuestStatus;
import pe.greenminds.ecomind_backend.quests.domain.repositories.CollabQuestMemberRepository;

import java.util.List;

@Service
public class QuestsRankingExternalService {

    private final CollabQuestMemberRepository collabQuestMemberRepository;

    public QuestsRankingExternalService(CollabQuestMemberRepository collabQuestMemberRepository) {
        this.collabQuestMemberRepository = collabQuestMemberRepository;
    }

    public int countCompletedCollabQuestsForUserIds(List<Long> userIds) {
        if (userIds.isEmpty()) return 0;
        return (int) collabQuestMemberRepository
                .findByUserIdInAndStatusAndSessionStatus(
                        userIds,
                        CollabMemberStatus.ACCEPTED,
                        CollabQuestStatus.COMPLETED
                )
                .stream()
                .map(member -> member.getSessionId())
                .distinct()
                .count();
    }
}
