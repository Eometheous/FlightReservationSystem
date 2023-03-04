import java.util.ArrayList;

/**
 * A record of a {@code User} which contains a users email, name, and password.
 * @param email the email of this {@code User}
 * @param name the name of this {@code User}
 * @param password the password of this {@code User}
 * @author Jonathan Stewart Thomas
 * @version 1.0.0.230304
 */
public record User(String email, String name, String password) {
    public static ArrayList<User> list = new ArrayList<>();

    /**
     * Adds a user with an email, name, and password to the {@code list}.
     * @param email the email of this {@code User}
     * @param name the name of this {@code User}
     * @param password the password of this {@code User}
     */
    public User(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
        list.add(this);
    }

    /**
     * Outputs {@code User} as a {@code String}.
     * @return {@code String} of {@code User}
     */
    @Override
    public String toString() {
        return String.format("%s\n%s\n%s\n", email, name, password);
    }
}
