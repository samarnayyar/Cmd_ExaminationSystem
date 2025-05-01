package auth;

import java.io.*;

public class User implements Authenticable, Displayable, Serializable {
    public static class Credentials implements Serializable {
        private String username;
        private String password;

        public Credentials(String username, String password) {
            if (username == null || username.trim().isEmpty()) {
                throw new IllegalArgumentException("Username cannot be empty");
            }
            if (password == null || password.trim().isEmpty()) {
                throw new IllegalArgumentException("Password cannot be empty");
            }
            this.username = username;
            this.password = password;
        }

        public String getUsername() { return username; }
        public String getPassword() { return password; }
    }

    protected Credentials credentials;

    public User(String username, String password) {
        this.credentials = new Credentials(username, password);
    }

    @Override
    public boolean login(String username, String password) {
        return credentials.getUsername().equalsIgnoreCase(username) && 
               credentials.getPassword().equals(password);
    }

    @Override
    public void displayInfo() {
        System.out.println("User: " + credentials.getUsername());
    }

    public String getUsername() {
        return credentials.getUsername();
    }
}
