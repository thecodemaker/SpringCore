package app.service;

/**
 * Service responsible of authenticating and authorizing an user.
 *
 * @author dvizireanu
 */
public interface SecurityService {

    /**
     * Authenticate user.
     *
     * @param username
     * @param password
     */
    void authenticate(String username, String password);
}
