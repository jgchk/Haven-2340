package net.curlybois.haven.model;

/**
 * Created by jake on 3/31/18.
 */

// TODO: Implement admin permissions
public class Admin extends User {
    public Admin(String email, String password) {
        super(email, password, ADMIN);
    }

    @Override
    public String toString() {
        return String.format("Admin[email=%s, password=%s, locked=%s]", getEmail(), getPassword(), isLocked());
    }
}
