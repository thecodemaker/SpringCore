package app.aop.security;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * Security aspect which check authentication / authorization in order to allow the access/execution of targeted resources.
 *
 * @author dvizireanu
 */
@Aspect
@Component
public class SecurityAspect {

    @Before("@annotation(Authenticated)")
    public void secure() {
        if (!SecurityContext.isAuthenticated()) {
            throw new app.exception.SecurityException("Not authenticated !");
        }
    }
}
