package pe.greenminds.ecomind_backend.quests.application.internal.commandservices;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import pe.greenminds.ecomind_backend.monetization.domain.model.valueobjects.MovementOrigin;
import pe.greenminds.ecomind_backend.profile.domain.repositories.UserRepository;
import pe.greenminds.ecomind_backend.quests.application.commandservices.MinigameAttemptCommandService;
import pe.greenminds.ecomind_backend.quests.application.internal.services.QuestRewardService;
import pe.greenminds.ecomind_backend.quests.domain.model.aggregates.MinigameAttempt;
import pe.greenminds.ecomind_backend.quests.domain.model.commands.CancelMinigameAttemptCommand;
import pe.greenminds.ecomind_backend.quests.domain.model.commands.CreateMinigameAttemptCommand;
import pe.greenminds.ecomind_backend.quests.domain.model.commands.FinishMinigameAttemptCommand;
import pe.greenminds.ecomind_backend.quests.domain.model.valueobjects.MinigameAttemptStatus;
import pe.greenminds.ecomind_backend.quests.domain.model.valueobjects.QuestType;
import pe.greenminds.ecomind_backend.quests.domain.repositories.MinigameAttemptRepository;
import pe.greenminds.ecomind_backend.quests.domain.repositories.MinigameRepository;
import pe.greenminds.ecomind_backend.quests.domain.repositories.QuestRepository;
import pe.greenminds.ecomind_backend.shared.application.result.ApplicationError;
import pe.greenminds.ecomind_backend.shared.application.result.Result;

import java.time.OffsetDateTime;
import java.util.Map;

@Service
public class MinigameAttemptCommandServiceImpl implements MinigameAttemptCommandService {
    private static final String MIN_SCORE_RULE = "minScore";
    private static final double[] DISCOUNTS = {1.0, 1.0, 0.8, 0.5, 0.2};

    private final MinigameAttemptRepository minigameAttemptRepository;
    private final MinigameRepository minigameRepository;
    private final QuestRepository questRepository;
    private final UserRepository userRepository;
    private final QuestRewardService questRewardService;

    public MinigameAttemptCommandServiceImpl(
            MinigameAttemptRepository minigameAttemptRepository,
            MinigameRepository minigameRepository,
            QuestRepository questRepository,
            UserRepository userRepository,
            QuestRewardService questRewardService
    ) {
        this.minigameAttemptRepository = minigameAttemptRepository;
        this.minigameRepository = minigameRepository;
        this.questRepository = questRepository;
        this.userRepository = userRepository;
        this.questRewardService = questRewardService;
    }

    @Override
    public Result<MinigameAttempt, ApplicationError> handle(
            CreateMinigameAttemptCommand command
    ) {
        if (!userRepository.existsById(command.userId())) {
            return Result.failure(ApplicationError.notFound("User", command.userId().toString()));
        }

        var quest = questRepository.findById(command.questId());
        if (quest.isEmpty()) {
            return Result.failure(ApplicationError.notFound("Quest", command.questId().toString()));
        }

        if (quest.get().getType() != QuestType.MINIGAME) {
            return Result.failure(
                    ApplicationError.businessRuleViolation(
                            "Quest must be a minigame quest",
                            "Quest %d is %s".formatted(command.questId(), quest.get().getType())
                    )
            );
        }

        if (quest.get().getMinigameId() == null) {
            return Result.failure(
                    ApplicationError.businessRuleViolation(
                            "Minigame quest must reference a minigame",
                            "Quest %d has no minigameId".formatted(command.questId())
                    )
            );
        }

        if (!minigameRepository.existsById(quest.get().getMinigameId())) {
            return Result.failure(
                    ApplicationError.notFound("Minigame", quest.get().getMinigameId().toString())
            );
        }

        if (minigameAttemptRepository.existsByUserIdAndStatus(
                command.userId(),
                MinigameAttemptStatus.STARTED
        )) {
            return Result.failure(
                    ApplicationError.conflict(
                            "MinigameAttempt",
                            "The user already has a started minigame attempt"
                    )
            );
        }

        try {
            return Result.success(
                    minigameAttemptRepository.save(
                            new MinigameAttempt(
                                    command.userId(),
                                    command.questId(),
                                    quest.get().getMinigameId()
                            )
                    )
            );
        } catch (IllegalArgumentException | NullPointerException exception) {
            return Result.failure(
                    ApplicationError.validationError("MinigameAttempt", exception.getMessage())
            );
        } catch (Exception exception) {
            return Result.failure(
                    ApplicationError.unexpected("MinigameAttempt creation", exception.getMessage())
            );
        }
    }

    @Transactional
    @Override
    public Result<MinigameAttempt, ApplicationError> handle(
            FinishMinigameAttemptCommand command
    ) {
        var attempt = minigameAttemptRepository.findById(command.attemptId());
        if (attempt.isEmpty()) {
            return Result.failure(
                    ApplicationError.notFound("MinigameAttempt", command.attemptId().toString())
            );
        }

        if (command.score() == null) {
            return Result.failure(
                    ApplicationError.validationError("score", "score must not be null")
            );
        }

        var quest = questRepository.findById(attempt.get().getQuestId());
        if (quest.isEmpty()) {
            return Result.failure(
                    ApplicationError.notFound("Quest", attempt.get().getQuestId().toString())
            );
        }

        var minigame = minigameRepository.findById(attempt.get().getMinigameId());
        if (minigame.isEmpty()) {
            return Result.failure(
                    ApplicationError.notFound(
                            "Minigame",
                            attempt.get().getMinigameId().toString()
                    )
            );
        }

        try {
            var successful = isSuccessful(command.score(), minigame.get().getCompletionRules());
            var givenGems = 0;
            var givenEcopoints = 0;

            if (successful) {
                var discount = calculateDiscount(attempt.get().getUserId(), attempt.get().getMinigameId());
                givenGems = applyRewardDiscount(quest.get().getGems(), discount, 5);
                givenEcopoints = applyRewardDiscount(quest.get().getEcopoints(), discount, 20);
            }

            attempt.get().finish(
                    command.score(),
                    command.metadata(),
                    givenGems,
                    givenEcopoints
            );

            var savedAttempt = minigameAttemptRepository.save(attempt.get());
            if (successful) {
                questRewardService.grantRewards(
                        savedAttempt.getUserId(),
                        savedAttempt.getGivenGems(),
                        savedAttempt.getGivenEcopoints(),
                        MovementOrigin.QUEST,
                        savedAttempt.getId()
                );
            }

            return Result.success(savedAttempt);
        } catch (IllegalArgumentException | NullPointerException exception) {
            return Result.failure(
                    ApplicationError.validationError("MinigameAttempt", exception.getMessage())
            );
        } catch (IllegalStateException exception) {
            return Result.failure(
                    ApplicationError.businessRuleViolation(
                            "Minigame attempt cannot be finished",
                            exception.getMessage()
                    )
            );
        } catch (Exception exception) {
            return Result.failure(
                    ApplicationError.unexpected("MinigameAttempt finish", exception.getMessage())
            );
        }
    }

    @Transactional
    @Override
    public Result<MinigameAttempt, ApplicationError> handle(
            CancelMinigameAttemptCommand command
    ) {
        var attempt = minigameAttemptRepository.findById(command.attemptId());
        if (attempt.isEmpty()) {
            return Result.failure(
                    ApplicationError.notFound("MinigameAttempt", command.attemptId().toString())
            );
        }

        try {
            attempt.get().cancel();
            return Result.success(minigameAttemptRepository.save(attempt.get()));
        } catch (IllegalStateException exception) {
            return Result.failure(
                    ApplicationError.businessRuleViolation(
                            "Minigame attempt cannot be cancelled",
                            exception.getMessage()
                    )
            );
        }
    }

    private boolean isSuccessful(Integer score, Map<String, Object> completionRules) {
        var minScore = completionRules.get(MIN_SCORE_RULE);
        if (!(minScore instanceof Number minScoreNumber)) {
            throw new IllegalArgumentException("completionRules.minScore must be numeric");
        }
        return score >= minScoreNumber.intValue();
    }

    private double calculateDiscount(Long userId, Long minigameId) {
        var rewardedAttempts = minigameAttemptRepository.findRewardedAttemptsSince(
                userId,
                minigameId,
                OffsetDateTime.now().minusHours(3)
        );
        var attemptNumber = rewardedAttempts.size() + 1;
        if (attemptNumber <= DISCOUNTS.length) {
            return DISCOUNTS[attemptNumber - 1];
        }
        return 0.0;
    }

    private Integer applyRewardDiscount(Integer baseReward, double discount, Integer minimumReward) {
        var reward = (int) Math.floor((baseReward == null ? 0 : baseReward) * discount);
        return reward < minimumReward ? 0 : reward;
    }
}
