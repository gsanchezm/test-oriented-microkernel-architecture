package tom.authentication.dao;

import java.io.Serializable;
import java.util.Objects;

public class UserCredentials implements Serializable {
    private String username;
    private String password;

    // Default Constructor (Needed for frameworks like Jackson, Gson, etc.)
    public UserCredentials() {}

    // Constructor with parameters
    public UserCredentials(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Override equals() and hashCode() for object comparison
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserCredentials that = (UserCredentials) o;
        return Objects.equals(username, that.username) &&
                Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password);
    }

    // Override toString() for easy logging/debugging
    @Override
    public String toString() {
        return "UserCredentials{" +
                "username='" + username + '\'' +
                ", password='****'" +  // Hide password for security reasons
                '}';
    }
}
