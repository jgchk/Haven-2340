package net.curlybois.haven.model;

/**
 * Created by jake on 3/31/18.
 */

// TODO: Implement ShelterEmployee permissions
public class ShelterEmployee extends User {
    public ShelterEmployee(String email, String password) {
        super(email, password, SHELTER_EMPLOYEE);
    }

    @Override
    public String toString() {
        return String.format("ShelterEmployee[email=%s, password=%s, locked=%s]", getEmail(), getPassword(), isLocked());
    }
}
