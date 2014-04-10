package app.aop.security;

import java.lang.annotation.*;

/**
 * Used to mark behaviors which should be executed only if authentication occur.
 *
 * @author dvizireanu
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Authenticated {
}
