package pe.greenminds.ecomind_backend.community.application.internal.commandservices;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import pe.greenminds.ecomind_backend.community.application.commandservices.EventRegistrationCommandService;
import pe.greenminds.ecomind_backend.community.domain.model.aggregates.EventRegistration;
import pe.greenminds.ecomind_backend.community.domain.model.commands.CancelEventRegistrationCommand;
import pe.greenminds.ecomind_backend.community.domain.model.commands.CreateEventRegistrationCommand;
import pe.greenminds.ecomind_backend.community.domain.repositories.EventRegistrationRepository;
import pe.greenminds.ecomind_backend.community.domain.repositories.EventRepository;
import pe.greenminds.ecomind_backend.shared.application.result.ApplicationError;
import pe.greenminds.ecomind_backend.shared.application.result.Result;

@Service
public class EventRegistrationCommandServiceImpl implements EventRegistrationCommandService {

    private final EventRegistrationRepository eventRegistrationRepository;
    private final EventRepository eventRepository;

    public EventRegistrationCommandServiceImpl(EventRegistrationRepository eventRegistrationRepository, EventRepository eventRepository) {
        this.eventRegistrationRepository = eventRegistrationRepository;
        this.eventRepository = eventRepository;
    }

    @Transactional
    @Override
    public Result<EventRegistration, ApplicationError> handle(CreateEventRegistrationCommand command) {
        if (!eventRepository.existsById(command.eventId())) {
            return Result.failure(
                    ApplicationError.notFound("Event", command.eventId().toString())
            );
        }

        if (eventRegistrationRepository.existsActiveByEventIdAndUserId(command.eventId(), command.userId())) {
            return Result.failure(
                    ApplicationError.conflict(
                            "EventRegistration",
                            "The user is already registered for this event"
                    )
            );
        }

        try {
            var registration = new EventRegistration(
                    command.eventId(),
                    command.userId(),
                    command.familyId(),
                    command.registrationType()
            );

            return Result.success(eventRegistrationRepository.save(registration));

        } catch (IllegalArgumentException | NullPointerException exception) {
            return Result.failure(
                    ApplicationError.validationError("EventRegistration", exception.getMessage())
            );
        } catch (Exception exception) {
            return Result.failure(
                    ApplicationError.unexpected("EventRegistration creation", exception.getMessage())
            );
        }
    }

    @Transactional
    @Override
    public Result<EventRegistration, ApplicationError> handle(CancelEventRegistrationCommand command) {
        var registration = eventRegistrationRepository.findById(command.registrationId());

        if (registration.isEmpty()) {
            return Result.failure(
                    ApplicationError.notFound("EventRegistration", command.registrationId().toString())
            );
        }

        if (!registration.get().getEventId().equals(command.eventId())) {
            return Result.failure(
                    ApplicationError.validationError(
                            "EventRegistration",
                            "The registration does not belong to the provided event"
                    )
            );
        }

        try {
            registration.get().cancel();
            return Result.success(eventRegistrationRepository.save(registration.get()));

        } catch (IllegalStateException exception) {
            return Result.failure(
                    ApplicationError.businessRuleViolation(
                            "EventRegistration cancellation",
                            exception.getMessage()
                    )
            );
        } catch (Exception exception) {
            return Result.failure(
                    ApplicationError.unexpected("EventRegistration cancellation", exception.getMessage())
            );
        }
    }
}