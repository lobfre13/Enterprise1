package unit;

import dto.Location;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by Fredrik on 03.12.2015.
 */
public class LocationTest {
    private Validator validator;

    @Before
    public void setUp() throws Exception {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Test
    public void RoomAndBuildingCantBeNull() throws Exception {
        Location location = new Location();
        Set<ConstraintViolation<Location>> violations = validator.validate(location);
        assertEquals(2, violations.size());
    }

    @Test
    public void validLocation() throws Exception {
        Location location = new Location("82", "Galleriet");
        Set<ConstraintViolation<Location>> violations = validator.validate(location);
        assertTrue(violations.isEmpty());

    }
}
