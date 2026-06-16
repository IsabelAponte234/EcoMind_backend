package pe.greenminds.ecomind_backend.profile.application.internal.commandservices;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import pe.greenminds.ecomind_backend.profile.application.commandservices.FamilyInvitationCommandService;
import pe.greenminds.ecomind_backend.profile.domain.model.aggregates.FamilyInvitation;
import pe.greenminds.ecomind_backend.profile.domain.model.aggregates.FamilyUser;
import pe.greenminds.ecomind_backend.profile.domain.model.commands.*;
import pe.greenminds.ecomind_backend.profile.domain.model.valueobjects.InvitationStatus;
import pe.greenminds.ecomind_backend.profile.domain.repositories.FamilyInvitationRepository;
import pe.greenminds.ecomind_backend.profile.domain.repositories.FamilyRepository;
import pe.greenminds.ecomind_backend.profile.domain.repositories.FamilyUserRepository;
import pe.greenminds.ecomind_backend.profile.domain.repositories.UserRepository;
import pe.greenminds.ecomind_backend.shared.application.result.ApplicationError;
import pe.greenminds.ecomind_backend.shared.application.result.Result;

@Service
public class FamilyInvitationCommandServiceImpl implements FamilyInvitationCommandService {
    private final FamilyInvitationRepository invitationRepository;
    private final FamilyUserRepository familyUserRepository;
    private final UserRepository userRepository;
    private final FamilyRepository familyRepository;

    public FamilyInvitationCommandServiceImpl(FamilyInvitationRepository invitationRepository,
                                              FamilyUserRepository familyUserRepository,
                                              UserRepository userRepository,
                                              FamilyRepository familyRepository) {
        this.invitationRepository = invitationRepository;
        this.familyUserRepository = familyUserRepository;
        this.userRepository = userRepository;
        this.familyRepository = familyRepository;
    }

    public Result<FamilyInvitation, ApplicationError> handle(CreateFamilyInvitationCommand command) {
        if (!familyRepository.existsById(command.familyId())) return Result.failure(ApplicationError.notFound("Family", command.familyId().toString()));
        if (!userRepository.existsById(command.inviterUserId())) return Result.failure(ApplicationError.notFound("User", command.inviterUserId().toString()));
        if (!userRepository.existsById(command.invitedUserId())) return Result.failure(ApplicationError.notFound("User", command.invitedUserId().toString()));
        if (familyUserRepository.existsByUserId(command.invitedUserId())) {
            return Result.failure(ApplicationError.businessRuleViolation("single-active-family", "Invited user already has an active family"));
        }
        var invitation = new FamilyInvitation(null, command.familyId(), command.inviterUserId(),
                command.invitedUserId(), command.invitedRole(), command.status(), command.createdAt(),
                command.respondedAt());
        return Result.success(invitationRepository.save(invitation));
    }

    public Result<FamilyInvitation, ApplicationError> handle(UpdateFamilyInvitationCommand command) {
        var invitation = invitationRepository.findById(command.invitationId());
        if (invitation.isEmpty()) return Result.failure(ApplicationError.notFound("FamilyInvitation", command.invitationId().toString()));
        invitation.get().update(command.familyId(), command.inviterUserId(), command.invitedUserId(),
                command.invitedRole(), command.status(), command.createdAt(), command.respondedAt());
        return Result.success(invitationRepository.save(invitation.get()));
    }

    @Transactional
    public Result<FamilyInvitation, ApplicationError> handle(AcceptFamilyInvitationCommand command) {
        var invitation = invitationRepository.findById(command.invitationId());
        if (invitation.isEmpty()) return Result.failure(ApplicationError.notFound("FamilyInvitation", command.invitationId().toString()));
        var value = invitation.get();
        if (value.getStatus() != InvitationStatus.PENDING) {
            return Result.failure(ApplicationError.businessRuleViolation("pending-family-invitation", "Only pending invitations can be accepted"));
        }
        if (command.acceptedByUserId() != null && !value.getInvitedUserId().equals(command.acceptedByUserId())) {
            return Result.failure(ApplicationError.businessRuleViolation("invitation-owner", "Only the invited user can accept this invitation"));
        }
        if (familyUserRepository.existsByUserId(value.getInvitedUserId())) {
            return Result.failure(ApplicationError.businessRuleViolation("single-active-family", "Invited user already has an active family"));
        }
        value.accept();
        var saved = invitationRepository.save(value);
        familyUserRepository.save(new FamilyUser(null, value.getInvitedUserId(), value.getFamilyId(), value.getInvitedRole(), value.getRespondedAt()));
        return Result.success(saved);
    }

    public Result<FamilyInvitation, ApplicationError> handle(RejectFamilyInvitationCommand command) {
        var invitation = invitationRepository.findById(command.invitationId());
        if (invitation.isEmpty()) return Result.failure(ApplicationError.notFound("FamilyInvitation", command.invitationId().toString()));
        if (invitation.get().getStatus() != InvitationStatus.PENDING) {
            return Result.failure(ApplicationError.businessRuleViolation("pending-family-invitation", "Only pending invitations can be rejected"));
        }
        invitation.get().reject();
        return Result.success(invitationRepository.save(invitation.get()));
    }

    public Result<FamilyInvitation, ApplicationError> handle(DeleteFamilyInvitationCommand command) {
        var invitation = invitationRepository.findById(command.invitationId());
        if (invitation.isEmpty()) return Result.failure(ApplicationError.notFound("FamilyInvitation", command.invitationId().toString()));
        invitationRepository.deleteById(command.invitationId());
        return Result.success(invitation.get());
    }
}
