package no.westerdals.lobfre13.lms.dao.interceptors;

import org.jboss.logging.Logger;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;


/**
 * Created by Fredrik on 05.12.2015.
 */

public class DAOInterceptor {

    private Logger logger = Logger.getLogger(getClass());

    @AroundInvoke
    public Object logDaoError(InvocationContext ic) throws Exception {
        try {
            return ic.proceed();
        } catch (IllegalArgumentException e) {
            logger.error(ic.getTarget().getClass().getSimpleName() + " - " + e.getMessage());
            throw e;
        }
    }
}
