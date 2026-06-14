package pe.greenminds.ecomind_backend.community.application.queryservices;

import pe.greenminds.ecomind_backend.community.domain.model.aggregates.Event;
import pe.greenminds.ecomind_backend.community.domain.model.queries.SearchEventsQuery;

import java.util.List;

public interface EventQueryService {
    List<Event> handle(SearchEventsQuery query);
}