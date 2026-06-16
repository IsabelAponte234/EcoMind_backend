package pe.greenminds.ecomind_backend.profile.application.internal.commandservices;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import pe.greenminds.ecomind_backend.profile.application.commandservices.UserCommandService;
import pe.greenminds.ecomind_backend.profile.domain.model.aggregates.User;
import pe.greenminds.ecomind_backend.profile.domain.model.commands.*;
import pe.greenminds.ecomind_backend.profile.domain.repositories.FamilyUserRepository;
import pe.greenminds.ecomind_backend.profile.domain.repositories.FriendRepository;
import pe.greenminds.ecomind_backend.profile.domain.repositories.UserRepository;
import pe.greenminds.ecomind_backend.shared.application.result.ApplicationError;
import pe.greenminds.ecomind_backend.shared.application.result.Result;

@Service
public class UserCommandServiceImpl implements UserCommandService {
    private final UserRepository userRepository;
    private final FamilyUserRepository familyUserRepository;
    private final FriendRepository friendRepository;

    public UserCommandServiceImpl(UserRepository userRepository, FamilyUserRepository familyUserRepository,
                                  FriendRepository friendRepository) {
        this.userRepository = userRepository;
        this.familyUserRepository = familyUserRepository;
        this.friendRepository = friendRepository;
    }

    public Result<User, ApplicationError> handle(CreateUserCommand command) {
        if (command.email() != null && userRepository.existsByEmail(command.email())) {
            return Result.failure(ApplicationError.conflict("User", "Email already exists"));
        }
        try {
            return Result.success(userRepository.save(new User(null, command.communityId(), command.email(),
                    command.birthDate(), command.name(), command.streak(), command.commitment(),
                    command.registeredAt(), command.gemBalance(), command.ecopoints(), command.lastStreakDate())));
        } catch (RuntimeException ex) {
            return Result.failure(ApplicationError.validationError("User", ex.getMessage()));
        }
    }

    public Result<User, ApplicationError> handle(UpdateUserCommand command) {
        var user = userRepository.findById(command.userId());
        if (user.isEmpty()) return Result.failure(ApplicationError.notFound("User", command.userId().toString()));
        user.get().update(command.communityId(), command.email(), command.birthDate(), command.name(),
                command.streak(), command.commitment(), command.registeredAt(), command.gemBalance(),
                command.ecopoints(), command.lastStreakDate());
        return Result.success(userRepository.save(user.get()));
    }

    public Result<User, ApplicationError> handle(UpdateUserCommitmentCommand command) {
        var user = userRepository.findById(command.userId());
        if (user.isEmpty()) return Result.failure(ApplicationError.notFound("User", command.userId().toString()));
        user.get().updateCommitment(command.commitment());
        return Result.success(userRepository.save(user.get()));
    }

    public Result<User, ApplicationError> handle(UpdateUserStatsCommand command) {
        var user = userRepository.findById(command.userId());
        if (user.isEmpty()) return Result.failure(ApplicationError.notFound("User", command.userId().toString()));
        user.get().updateStats(command.gemBalance(), command.ecopoints(), command.streak(), command.lastStreakDate());
        return Result.success(userRepository.save(user.get()));
    }

    @Transactional
    public Result<User, ApplicationError> handle(DeleteUserCommand command) {
        var user = userRepository.findById(command.userId());
        if (user.isEmpty()) return Result.failure(ApplicationError.notFound("User", command.userId().toString()));
        familyUserRepository.deleteByUserId(command.userId());
        friendRepository.deleteByUserId(command.userId());
        userRepository.deleteById(command.userId());
        return Result.success(user.get());
    }
}
