package unit;

import dto.Event;
import dto.Location;
import dto.Subject;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Calendar;
import java.util.Set;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

/**
 * Created by Fredrik on 03.12.2015.
 */
public class EventTest {
    private Validator validator;
    private Event event;

    @Before
    public void setUp() throws Exception {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
        event = new Event();
        event.setTitle("testEvent");
        event.setDescription("TestDesc");
        event.setType(Event.Type.LECTURE);

        Calendar cal = Calendar.getInstance();
        event.setStartTime(cal.getTime());
        event.setEndTime(cal.getTime());
        event.setSubject(getASubject());
    }

    @Test
    public void typeCantBeNull() throws Exception {
        event.setType(null);

        Set<ConstraintViolation<Event>> violations = validator.validate(event);
        assertEquals(1, violations.size());
    }

    @Test
    public void titleTooShort() throws Exception {
        event.setTitle("Shrt");
        Set<ConstraintViolation<Event>> violations = validator.validate(event);
        assertEquals(1, violations.size());
    }

    @Test
    public void titleTooLong() throws Exception {
        event.setTitle("A too long title should be invalid, which is more than 25 chars");
        Set<ConstraintViolation<Event>> violations = validator.validate(event);
        assertEquals(1, violations.size());
    }

    @Test
    public void titleCantBeNull() throws Exception {
        event.setTitle(null);
        Set<ConstraintViolation<Event>> violations = validator.validate(event);
        assertEquals(1, violations.size());

    }

    @Test
    public void descriptionNullable() throws Exception {
        event.setDescription(null);
        Set<ConstraintViolation<Event>> violations = validator.validate(event);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void descriptionMax100Chars() throws Exception {
        event.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque dignissim cursus " +
                "posuere. Etiam egestas pulvinar malesuada. Integer ultrices varius neque, ut volutpat risus ultricies " +
                "eu. Etiam auctor, lacus non gravida accumsan, diam nisl dapibus sem, a semper ipsum dui eu velit. " +
                "Donec molestie, ipsum a consectetur hendrerit, ex eros posuere tortor, in volutpat ligula nisi eget " +
                "justo. Sed at vulputate enim. Integer at urna arcu. In at leo non nunc pulvinar egestas id at elit." +
                "Sed mattis nunc sit amet massa rutrum aliquam. Donec consequat ex eu malesuada dignissim. " +
                "Mauris condimentum at elit nec eleifend. Aliquam accumsan, metus eget convallis.");

        Set<ConstraintViolation<Event>> violations = validator.validate(event);
        assertEquals(1, violations.size());
    }

    @Test
    public void validEvent() throws Exception {
        Set<ConstraintViolation<Event>> violations = validator.validate(event);
        assertTrue(violations.isEmpty());
    }

    private Subject getASubject(){
        Subject subject = new Subject();
        subject.setName("PG5100");
        subject.setLocation(new Location());
        subject.getLocation().setBuilding("Galleriet");
        subject.getLocation().setRoom("82");
        return subject;
    }
}
