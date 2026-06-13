package pe.greenminds.ecomind_backend.community.application.internal.commandservices;

import org.springframework.stereotype.Service;
import pe.greenminds.ecomind_backend.community.application.commandservices.EventCommandService;
import pe.greenminds.ecomind_backend.community.domain.model.aggregates.Event;
import pe.greenminds.ecomind_backend.community.domain.model.commands.CreateEventCommand;
import pe.greenminds.ecomind_backend.community.domain.model.commands.DeleteEventCommand;
import pe.greenminds.ecomind_backend.community.domain.repositories.EventRepository;
import pe.greenminds.ecomind_backend.shared.application.result.ApplicationError;
import pe.greenminds.ecomind_backend.shared.application.result.Result;

@Service
public class EventCommandServiceImpl implements EventCommandService {

    private final EventRepository eventRepository;

    public EventCommandServiceImpl(EventRepository eventRepository) {this.eventRepository = eventRepository;}

    @Override
    public Result<Event, ApplicationError> handle(CreateEventCommand command) {
        try {
            var event = new Event(
                    command.communityId(),
                    command.authorId(),
                    command.name(),
                    command.description(),
                    command.date(),
                    command.startTime(),
                    command.endTime(),
                    command.location(),
                    command.latitude(),
                    command.longitude(),
                    command.capacity(),
                    command.imageUrl()
            );

            return Result.success(eventRepository.save(event));
        } catch (IllegalArgumentException | NullPointerException e) {
            return Result.failure(
                    ApplicationError.validationError("Event", e.getMessage())
            );
        } catch (Exception e) {
            return Result.failure(
                    ApplicationError.unexpected("Event creation", e.getMessage())
            );
        }
    }

    @Override
    public Result<Void, ApplicationError> handle(DeleteEventCommand command) {
        try {
            if (!eventRepository.existsById(command.id())) {
                return Result.failure(
                        ApplicationError.notFound("Event", command.id().toString())
                );
            }

            eventRepository.deleteById(command.id());
            return Result.success(null);
        } catch (Exception e) {
            return Result.failure(
                    ApplicationError.unexpected("Event deletion", e.getMessage())
            );
        }
    }
}