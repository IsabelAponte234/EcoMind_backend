package pe.greenminds.ecomind_backend.profile.infrastructure.persistence.jpa.adapters;

import org.springframework.stereotype.Repository;
import pe.greenminds.ecomind_backend.profile.domain.model.aggregates.FamilyInvitation;
import pe.greenminds.ecomind_backend.profile.domain.model.valueobjects.InvitationStatus;
import pe.greenminds.ecomind_backend.profile.domain.repositories.FamilyInvitationRepository;
import pe.greenminds.ecomind_backend.profile.infrastructure.persistence.jpa.assemblers.FamilyInvitationPersistenceAssembler;
import pe.greenminds.ecomind_backend.profile.infrastructure.persistence.jpa.repositories.FamilyInvitationPersistenceRepository;

import java.util.List;
import java.util.Optional;

@Repository
public class FamilyInvitationRepositoryImpl implements FamilyInvitationRepository {
    private final FamilyInvitationPersistenceRepository repository;

    public FamilyInvitationRepositoryImpl(FamilyInvitationPersistenceRepository repository) {
        this.repository = repository;
    }

    public List<FamilyInvitation> findAll() {
        return repository.findAll().stream().map(FamilyInvitationPersistenceAssembler::toDomainFromPersistence).toList();
    }

    public Optional<FamilyInvitation> findById(Long id) {
        return repository.findById(id).map(FamilyInvitationPersistenceAssembler::toDomainFromPersistence);
    }

    public List<FamilyInvitation> search(Long invitedUserId, Long inviterUserId, InvitationStatus status) {
        return repository.search(invitedUserId, inviterUserId, status).stream()
                .map(FamilyInvitationPersistenceAssembler::toDomainFromPersistence).toList();
    }

    public FamilyInvitation save(FamilyInvitation invitation) {
        return FamilyInvitationPersistenceAssembler.toDomainFromPersistence(
                repository.save(FamilyInvitationPersistenceAssembler.toPersistenceFromDomain(invitation)));
    }

    public void deleteById(Long id) { repository.deleteById(id); }
    public void deleteByFamilyId(Long familyId) { repository.deleteByFamilyId(familyId); }
    public boolean existsById(Long id) { return repository.existsById(id); }
}
