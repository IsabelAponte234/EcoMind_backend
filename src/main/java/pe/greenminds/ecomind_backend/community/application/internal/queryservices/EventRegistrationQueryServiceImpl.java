package pe.greenminds.ecomind_backend.community.application.internal.queryservices;

import org.springframework.stereotype.Service;
import pe.greenminds.ecomind_backend.community.application.queryservices.EventRegistrationQueryService;
import pe.greenminds.ecomind_backend.community.domain.model.aggregates.EventRegistration;
import pe.greenminds.ecomind_backend.community.domain.model.queries.GetEventRegistrationByEventIdAndUserIdQuery;
import pe.greenminds.ecomind_backend.community.domain.model.queries.SearchEventRegistrationsQuery;
import pe.greenminds.ecomind_backend.community.domain.repositories.EventRegistrationRepository;

import java.util.List;
import java.util.Optional;

@Service
public class EventRegistrationQueryServiceImpl implements EventRegistrationQueryService {

    private final EventRegistrationRepository eventRegistrationRepository;

    public EventRegistrationQueryServiceImpl(EventRegistrationRepository eventRegistrationRepository) { this.eventRegistrationRepository = eventRegistrationRepository;}

    @Override
    public List<EventRegistration> handle(SearchEventRegistrationsQuery query) {
        return eventRegistrationRepository.search(
                query.eventId(),
                query.userId(),
                query.familyId(),
                query.status()
        );
    }

    @Override
    public Optional<EventRegistration> handle(GetEventRegistrationByEventIdAndUserIdQuery query) {
        return eventRegistrationRepository.findByEventIdAndUserId(
                query.eventId(),
                query.userId()
        );
    }
}