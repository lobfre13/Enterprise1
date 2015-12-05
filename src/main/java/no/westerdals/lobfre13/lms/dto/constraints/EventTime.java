package no.westerdals.lobfre13.lms.dto.constraints;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Fredrik on 05.12.2015.
 */
@Constraint(validatedBy = EventTimeValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EventTime {
    String message() default "{no.westerdals.lobfre13.lms.dto.EventTime.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
