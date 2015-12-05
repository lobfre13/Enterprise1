package dto.constraints;

import dto.Event;

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
        if (event.getStartTime() == null || event.getEndTime() == null)
            return true;
        else return event.getStartTime().before(event.getEndTime());
    }
}
