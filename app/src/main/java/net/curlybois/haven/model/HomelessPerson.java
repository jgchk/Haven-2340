package net.curlybois.haven.model;

/**
 * Created by jake on 3/31/18.
 */

public class HomelessPerson extends User {
    /**
     * Create a new HomelessPerson
     * @param email the homeless person email
     * @param password the homeless person password
     */
    public HomelessPerson(String email, String password) {
        super(email, password, HOMELESS_PERSON);
    }

    @Override
    public String toString() {
        return String.format("HomelessPerson[email=%s, password=%s, locked=%s]", getEmail(),
                getPassword(), isLocked());
    }
}
