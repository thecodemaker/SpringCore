package app.exception;

/**
 * Exception describing an security error case.
 *
 * @author dvizireanu
 */
public class SecurityException extends RuntimeException {

    public SecurityException(String message) {
        super(message);
    }
}
