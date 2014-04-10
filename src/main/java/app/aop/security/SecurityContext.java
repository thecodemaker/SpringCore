package app.aop.security;

/**
 * Security context, which holds information about logged user.
 *
 * @author dvizireanu
 */
public class SecurityContext {

    private static final ThreadLocal<String> currentUser = new ThreadLocal<>();

    public static boolean isAuthenticated() {
        return currentUser.get() != null;
    }

    public static void setCurrentUser(String username) {
        currentUser.set(username);
    }

    public static void clear() {
        currentUser.remove();
    }
}
