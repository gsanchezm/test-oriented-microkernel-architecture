package tom.authentication.dao;

import java.io.Serializable;
import java.util.Objects;

public class UserInformation implements Serializable {
    private String username;
    private String password;
    String firstName;
    String lastName;
    String postalCode;

    // Default Constructor (Needed for frameworks like Jackson, Gson, etc.)
    public UserInformation() {}

    // Constructor with parameters
    public UserInformation(String username, String password, String firstName, String lastName, String postalCode) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.postalCode = postalCode;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UserInformation that = (UserInformation) o;
        return Objects.equals(username, that.username) && Objects.equals(password, that.password) && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(postalCode, that.postalCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, firstName, lastName, postalCode);
    }

    @Override
    public String toString() {
        return "UserInformation{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", postalCode='" + postalCode + '\'' +
                '}';
    }
}
