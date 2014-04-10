package app.service;

import app.aop.security.SecurityContext;
import app.exception.SecurityException;
import org.springframework.stereotype.Service;

/**
 * @author dvizireanu
 */
@Service
public class SecurityServiceImpl implements SecurityService {

    @Override
    public void authenticate(String username, String password) {

        if ("guest".equals(username) && "endava".equals(password)) {
            SecurityContext.setCurrentUser(username);
        } else {
            throw new SecurityException("Wrong credentials !");
        }

    }
}
