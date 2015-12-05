package unit;

import no.westerdals.lobfre13.lms.dto.Location;
import no.westerdals.lobfre13.lms.dto.Subject;
import no.westerdals.lobfre13.lms.dto.User;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by Fredrik on 24.11.2015.
 */
public class SubjectTest {
    private Validator validator;

    @Before
    public void setUp() throws Exception {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Test
    public void invalidName() throws Exception {
        Subject subject = new Subject();
        Set<ConstraintViolation<Subject>> violations = validator.validate(subject);
        assertEquals(2, violations.size());
    }

    @Test
    public void validName() throws Exception {
        Subject subject = new Subject();
        subject.setName("PG5600");
        Set<ConstraintViolation<Subject>> violations = validator.validate(subject);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void tooManyUsers() throws Exception {
        Subject subject = new Subject();
        subject.setName("PG5600");
        List<User> users = new ArrayList<>();
        for(int i = 0; i < 101; i++) users.add(new User());
        subject.setUsers(users);
        Set<ConstraintViolation<Subject>> violations = validator.validate(subject);
        assertEquals(1, violations.size());
    }

    @Test
    public void maxUsers() throws Exception {
        Subject subject = new Subject();
        subject.setName("PG5600");
        List<User> users = new ArrayList<>();
        for(int i = 0; i < 100; i++) users.add(new User());
        subject.setUsers(users);
        Set<ConstraintViolation<Subject>> violations = validator.validate(subject);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void invalidLocation() throws Exception {
        Subject subject = new Subject();
        subject.setName("PG5600");
        subject.setLocation(new Location());

        Set<ConstraintViolation<Subject>> violations = validator.validate(subject);
        assertEquals(4, violations.size());
    }
}
