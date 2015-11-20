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
        User user = new User(0, "mail", "d", User.Role.STUDENT);
        Set<ConstraintViolation<User>> violations = validator.validate(user, Email.class);
        assertEquals(1, violations.size());
    }

    @Test
    public void invalidEmail_null() throws Exception {
        User user = new User(0, null, "Dd2", User.Role.STUDENT);
        Set<ConstraintViolation<User>> violations = validator.validate(user, Email.class);
        assertEquals(1, violations.size());
    }

    @Test
    public void invalidPassword() throws Exception {
        User user = new User(0, null, "lowercase1", User.Role.STUDENT);
        Set<ConstraintViolation<User>> violations = validator.validate(user, Password.class);
        assertEquals(1, violations.size());
    }

    @Test
    public void validPassword() throws Exception {
        User user = new User(0, null, "lowerUPPER123", User.Role.STUDENT);
        Set<ConstraintViolation<User>> violations = validator.validate(user, Password.class);
        assertEquals(0, violations.size());
    }
}