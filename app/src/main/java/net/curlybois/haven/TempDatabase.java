package net.curlybois.haven;

import net.curlybois.haven.model.Shelter;
import net.curlybois.haven.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jake on 2/20/2018.
 */

public class TempDatabase {
    private static Map<String, User> USERS = new HashMap<>();



    private static ArrayList<Shelter> SHELTERS= new ArrayList<>();

    public static boolean addShelter(Shelter shelter) {
        return SHELTERS.add(shelter);
    }
    public static boolean removeShelter(Shelter shelter) {
        return SHELTERS.remove(shelter);
    }
    public static ArrayList<Shelter> getShelters() {
        return SHELTERS;
    }

    /**
     * Adds a new user
     *
     * @param user the user to add
     * @return false if a user with the same email already exists, true otherwise
     */
    public static boolean addUser(User user) {
        if (USERS.containsKey(user.getEmail())) {
            return false;
        }

        USERS.put(user.getEmail(), user);
        return true;
    }

    /**
     * Checks whether a given login is valid
     *
     * @param email email for login
     * @param password password for login
     * @return whether the login is valid (found in the user store)
     */
    public static boolean isValidLogin(String email, String password) {
        User user = USERS.get(email);
        return user != null && password.equals(user.getPassword());
    }
}
