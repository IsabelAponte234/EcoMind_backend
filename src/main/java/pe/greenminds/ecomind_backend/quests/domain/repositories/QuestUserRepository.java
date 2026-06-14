package pe.greenminds.ecomind_backend.quests.domain.repositories;

import pe.greenminds.ecomind_backend.quests.domain.model.aggregates.QuestUser;
import pe.greenminds.ecomind_backend.quests.domain.model.valueobjects.QuestStatus;

import java.util.List;
import java.util.Optional;

public interface QuestUserRepository {
    QuestUser save(QuestUser questUser);
    Optional<QuestUser> findById(Long id);

    Optional<QuestUser> findByUserIdAndQuestId(Long userId, Long questId);

    List<QuestUser> findByQuestId(Long questId);

    boolean existsByUserIdAndQuestId(Long userId, Long questId);

    boolean existsByUserIdAndQuestIdAndStatusAndIdNot(
            Long userId,
            Long questId,
            QuestStatus status,
            Long excludedQuestUserId
    );

    void deleteById(Long id);

    void deleteByQuestId(Long questId);

    List<QuestUser> findByUserIdAndStatus(Long userId, QuestStatus questStatus);

}
