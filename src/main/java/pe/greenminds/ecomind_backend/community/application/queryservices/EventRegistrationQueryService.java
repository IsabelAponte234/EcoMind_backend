package pe.greenminds.ecomind_backend.community.application.queryservices;

import pe.greenminds.ecomind_backend.community.domain.model.aggregates.EventRegistration;
import pe.greenminds.ecomind_backend.community.domain.model.queries.GetEventRegistrationByEventIdAndUserIdQuery;
import pe.greenminds.ecomind_backend.community.domain.model.queries.SearchEventRegistrationsQuery;

import java.util.List;
import java.util.Optional;

public interface EventRegistrationQueryService {
    List<EventRegistration> handle(SearchEventRegistrationsQuery query);
    Optional<EventRegistration> handle(GetEventRegistrationByEventIdAndUserIdQuery query);
}