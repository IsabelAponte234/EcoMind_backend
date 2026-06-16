package pe.greenminds.ecomind_backend.profile.application.internal.commandservices;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import pe.greenminds.ecomind_backend.profile.application.commandservices.FamilyCommandService;
import pe.greenminds.ecomind_backend.profile.domain.model.aggregates.Family;
import pe.greenminds.ecomind_backend.profile.domain.model.commands.CreateFamilyCommand;
import pe.greenminds.ecomind_backend.profile.domain.model.commands.DeleteFamilyCommand;
import pe.greenminds.ecomind_backend.profile.domain.model.commands.UpdateFamilyCommand;
import pe.greenminds.ecomind_backend.profile.domain.model.commands.UpdateFamilyCommitmentCommand;
import pe.greenminds.ecomind_backend.profile.domain.repositories.FamilyInvitationRepository;
import pe.greenminds.ecomind_backend.profile.domain.repositories.FamilyRepository;
import pe.greenminds.ecomind_backend.profile.domain.repositories.FamilyUserRepository;
import pe.greenminds.ecomind_backend.shared.application.result.ApplicationError;
import pe.greenminds.ecomind_backend.shared.application.result.Result;

@Service
public class FamilyCommandServiceImpl implements FamilyCommandService {
    private final FamilyRepository familyRepository;
    private final FamilyUserRepository familyUserRepository;
    private final FamilyInvitationRepository familyInvitationRepository;

    public FamilyCommandServiceImpl(FamilyRepository familyRepository, FamilyUserRepository familyUserRepository,
                                    FamilyInvitationRepository familyInvitationRepository) {
        this.familyRepository = familyRepository;
        this.familyUserRepository = familyUserRepository;
        this.familyInvitationRepository = familyInvitationRepository;
    }

    public Result<Family, ApplicationError> handle(CreateFamilyCommand command) {
        try {
            return Result.success(familyRepository.save(new Family(null, command.name(), command.commitment())));
        } catch (RuntimeException ex) {
            return Result.failure(ApplicationError.validationError("Family", ex.getMessage()));
        }
    }

    public Result<Family, ApplicationError> handle(UpdateFamilyCommand command) {
        var family = familyRepository.findById(command.familyId());
        if (family.isEmpty()) return Result.failure(ApplicationError.notFound("Family", command.familyId().toString()));
        try {
            family.get().update(command.name(), command.commitment());
            return Result.success(familyRepository.save(family.get()));
        } catch (RuntimeException ex) {
            return Result.failure(ApplicationError.validationError("Family", ex.getMessage()));
        }
    }

    public Result<Family, ApplicationError> handle(UpdateFamilyCommitmentCommand command) {
        var family = familyRepository.findById(command.familyId());
        if (family.isEmpty()) return Result.failure(ApplicationError.notFound("Family", command.familyId().toString()));
        family.get().updateCommitment(command.commitment());
        return Result.success(familyRepository.save(family.get()));
    }

    @Transactional
    public Result<Family, ApplicationError> handle(DeleteFamilyCommand command) {
        var family = familyRepository.findById(command.familyId());
        if (family.isEmpty()) return Result.failure(ApplicationError.notFound("Family", command.familyId().toString()));
        familyInvitationRepository.deleteByFamilyId(command.familyId());
        familyUserRepository.deleteByFamilyId(command.familyId());
        familyRepository.deleteById(command.familyId());
        return Result.success(family.get());
    }
}
