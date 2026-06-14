package pe.greenminds.ecomind_backend.community.application.internal.queryservices;

import org.springframework.stereotype.Service;
import pe.greenminds.ecomind_backend.community.application.queryservices.EventQueryService;
import pe.greenminds.ecomind_backend.community.domain.model.aggregates.Event;
import pe.greenminds.ecomind_backend.community.domain.model.queries.SearchEventsQuery;
import pe.greenminds.ecomind_backend.community.domain.repositories.EventRepository;

import java.util.List;

@Service
public class EventQueryServiceImpl implements EventQueryService {

    private final EventRepository eventRepository;

    public EventQueryServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public List<Event> handle(SearchEventsQuery query) {
        return eventRepository.search(
                query.communityId(),
                query.authorId(),
                query.name(),
                query.date(),
                query.location()
        );
    }
}