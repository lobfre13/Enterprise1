package unit;

import dto.User;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by Fredrik on 09.10.2015.
 */
public class UserTest {

    private Validator validator;

    @Before
    public void setUp() throws Exception {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Test
    public void invalidEmail() throws Exception {
        User user = new User(0, "mail", "validPassword123", User.Role.STUDENT);
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size());
    }

    @Test
    public void invalidEmail_null() throws Exception {
        User user = new User(0, null, "validPassword123", User.Role.STUDENT);
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size());
    }

    @Test
    public void invalidPassword() throws Exception {
        User user = new User(0, "f@f.com", "lowercase1", User.Role.STUDENT);
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size());
    }

    @Test
    public void validPassword() throws Exception {
        User user = new User(0, "f@f.com", "lowerUPPER123", User.Role.STUDENT);
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void passwordTooShort() throws Exception {
        User user = new User(0, "f@f.com", "sHor1", User.Role.STUDENT);
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size());
    }
}