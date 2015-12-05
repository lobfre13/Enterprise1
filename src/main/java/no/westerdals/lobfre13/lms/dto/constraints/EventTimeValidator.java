package no.westerdals.lobfre13.lms.dto.constraints;

import no.westerdals.lobfre13.lms.dto.Event;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by Fredrik on 05.12.2015.
 */
public class EventTimeValidator implements ConstraintValidator<EventTime, Event> {
    @Override
    public void initialize(EventTime eventTime) {

    }

    @Override
    public boolean isValid(Event event, ConstraintValidatorContext constraintValidatorContext) {
        return event.getStartTime() == null || event.getEndTime() == null || event.getStartTime().before(event.getEndTime());
    }
}
