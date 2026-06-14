package pe.greenminds.ecomind_backend.quests.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.greenminds.ecomind_backend.quests.domain.model.valueobjects.QuestStatus;
import pe.greenminds.ecomind_backend.quests.infrastructure.persistence.jpa.entities.QuestUserPersistenceEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestUserPersistenceRepository extends JpaRepository<QuestUserPersistenceEntity, Long> {
    Optional<QuestUserPersistenceEntity> findByUserIdAndQuestId(
            Long userId,
            Long questId
    );

    List<QuestUserPersistenceEntity> findByQuestId(Long questId);

    boolean existsByUserIdAndQuestId(
            Long userId,
            Long questId
    );

    boolean existsByUserIdAndQuestIdAndStatusAndIdNot(
            Long userId,
            Long questId,
            QuestStatus status,
            Long excludedQuestUserId
    );

    void deleteByQuestId(Long questId);

    List<QuestUserPersistenceEntity> findByUserIdAndStatus(
            Long userId,
            QuestStatus status
    );
}
