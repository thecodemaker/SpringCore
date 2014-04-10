package app.aop.security;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * Security aspect which check authentication / authorization in order to allow the access/execution of targeted resources.
 *
 * @author dvizireanu
 */
// TODO  -> 2. make class as aspect
// TODO  -> 3. make class as aspect
public class SecurityAspect {

    // TODO  -> 5. define it as advice and also define it's pointcut that should intercept all methods annotated with @Authenticated
    public void secure() {
        if (!SecurityContext.isAuthenticated()) {
            throw new app.exception.SecurityException("Not authenticated !");
        }
    }
}
