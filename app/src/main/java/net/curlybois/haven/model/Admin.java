package net.curlybois.haven.model;

/**
 * Created by jake on 3/31/18.
 */

public class Admin extends User {
    /**
     * Create a new Admin
     * @param email the admin email
     * @param password the admin password
     */
    public Admin(String email, String password) {
        super(email, password, ADMIN);
    }

    @Override
    public String toString() {
        return String.format("Admin[email=%s, password=%s, locked=%s]", getEmail(), getPassword(),
                isLocked());
    }
}
