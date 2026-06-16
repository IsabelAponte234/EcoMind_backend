package pe.greenminds.ecomind_backend.profile.application.queryservices;

import pe.greenminds.ecomind_backend.profile.domain.model.aggregates.FamilyInvitation;
import pe.greenminds.ecomind_backend.profile.domain.model.queries.GetFamilyInvitationsQuery;

import java.util.List;

public interface FamilyInvitationQueryService {
    List<FamilyInvitation> handle(GetFamilyInvitationsQuery query);
}
