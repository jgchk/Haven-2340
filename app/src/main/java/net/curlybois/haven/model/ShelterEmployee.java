package net.curlybois.haven.model;

/**
 * Created by jake on 3/31/18.
 */

public class ShelterEmployee extends User {
    /**
     * Create a new ShelterEmployee
     * @param email the shelter employee email
     * @param password the shelter employee password
     */
    public ShelterEmployee(String email, String password) {
        super(email, password, SHELTER_EMPLOYEE);
    }

    @Override
    public String toString() {
        return String.format("ShelterEmployee[email=%s, password=%s, locked=%s]", getEmail(),
                getPassword(), isLocked());
    }
}
