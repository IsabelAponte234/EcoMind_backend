package pe.greenminds.ecomind_backend.profile.application.internal.commandservices;

import org.springframework.stereotype.Service;
import pe.greenminds.ecomind_backend.profile.application.commandservices.FamilyUserCommandService;
import pe.greenminds.ecomind_backend.profile.domain.model.aggregates.FamilyUser;
import pe.greenminds.ecomind_backend.profile.domain.model.commands.CreateFamilyUserCommand;
import pe.greenminds.ecomind_backend.profile.domain.model.commands.DeleteFamilyUserCommand;
import pe.greenminds.ecomind_backend.profile.domain.model.commands.UpdateFamilyUserCommand;
import pe.greenminds.ecomind_backend.profile.domain.repositories.FamilyRepository;
import pe.greenminds.ecomind_backend.profile.domain.repositories.FamilyUserRepository;
import pe.greenminds.ecomind_backend.profile.domain.repositories.UserRepository;
import pe.greenminds.ecomind_backend.shared.application.result.ApplicationError;
import pe.greenminds.ecomind_backend.shared.application.result.Result;

@Service
public class FamilyUserCommandServiceImpl implements FamilyUserCommandService {
    private final FamilyUserRepository familyUserRepository;
    private final UserRepository userRepository;
    private final FamilyRepository familyRepository;

    public FamilyUserCommandServiceImpl(FamilyUserRepository familyUserRepository, UserRepository userRepository,
                                        FamilyRepository familyRepository) {
        this.familyUserRepository = familyUserRepository;
        this.userRepository = userRepository;
        this.familyRepository = familyRepository;
    }

    public Result<FamilyUser, ApplicationError> handle(CreateFamilyUserCommand command) {
        if (!userRepository.existsById(command.userId())) return Result.failure(ApplicationError.notFound("User", command.userId().toString()));
        if (!familyRepository.existsById(command.familyId())) return Result.failure(ApplicationError.notFound("Family", command.familyId().toString()));
        if (familyUserRepository.existsByUserIdAndFamilyId(command.userId(), command.familyId())) {
            return Result.failure(ApplicationError.conflict("FamilyUser", "User already belongs to this family"));
        }
        if (familyUserRepository.existsByUserId(command.userId())) {
            return Result.failure(ApplicationError.businessRuleViolation("single-active-family", "User already has an active family"));
        }
        return Result.success(familyUserRepository.save(new FamilyUser(null, command.userId(), command.familyId(),
                command.familyRole(), command.joinedAt())));
    }

    public Result<FamilyUser, ApplicationError> handle(UpdateFamilyUserCommand command) {
        var familyUser = familyUserRepository.findById(command.familyUserId());
        if (familyUser.isEmpty()) return Result.failure(ApplicationError.notFound("FamilyUser", command.familyUserId().toString()));
        familyUser.get().update(command.familyRole(), command.joinedAt());
        return Result.success(familyUserRepository.save(familyUser.get()));
    }

    public Result<FamilyUser, ApplicationError> handle(DeleteFamilyUserCommand command) {
        var familyUser = familyUserRepository.findById(command.familyUserId());
        if (familyUser.isEmpty()) return Result.failure(ApplicationError.notFound("FamilyUser", command.familyUserId().toString()));
        familyUserRepository.deleteById(command.familyUserId());
        return Result.success(familyUser.get());
    }
}
