package pe.greenminds.ecomind_backend.profile.application.internal.queryservices;

import org.springframework.stereotype.Service;
import pe.greenminds.ecomind_backend.profile.application.queryservices.FamilyInvitationQueryService;
import pe.greenminds.ecomind_backend.profile.domain.model.aggregates.FamilyInvitation;
import pe.greenminds.ecomind_backend.profile.domain.model.queries.GetFamilyInvitationsQuery;
import pe.greenminds.ecomind_backend.profile.domain.repositories.FamilyInvitationRepository;

import java.util.List;

@Service
public class FamilyInvitationQueryServiceImpl implements FamilyInvitationQueryService {
    private final FamilyInvitationRepository repository;

    public FamilyInvitationQueryServiceImpl(FamilyInvitationRepository repository) {
        this.repository = repository;
    }

    public List<FamilyInvitation> handle(GetFamilyInvitationsQuery query) {
        return repository.search(query.invitedUserId(), query.inviterUserId(), query.status());
    }
}
