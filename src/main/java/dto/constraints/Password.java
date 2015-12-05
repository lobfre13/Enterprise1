package dto.constraints;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Fredrik on 03.12.2015.
 */
@Constraint(validatedBy = {})
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@ReportAsSingleViolation
@NotNull
@Size(min = 8)
@Pattern.List({
        @Pattern(regexp = "^.*[a-z].*$"),
        @Pattern(regexp = "^.*[A-Z].*$"),
        @Pattern(regexp = "^.*[0-9].*$")
})
public @interface Password {
    String message() default "{no.westerdals.lobfre13.lms.Password.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
