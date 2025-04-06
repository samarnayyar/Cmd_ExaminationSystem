package auth;

public interface Authenticable {
    boolean login(String username, String password);
}