package no.westerdals.lobfre13.lms.dao.user;

import javax.inject.Qualifier;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Fredrik on 08.10.2015.
 */
@Retention(RetentionPolicy.RUNTIME)
@Qualifier
@Target({ElementType.FIELD, ElementType.TYPE, ElementType.METHOD})
public @interface JPAUser {
}
