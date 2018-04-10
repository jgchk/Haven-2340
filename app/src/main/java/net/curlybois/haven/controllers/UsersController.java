package net.curlybois.haven.controllers;

import android.content.Context;
import android.content.SharedPreferences;

import net.curlybois.haven.model.Admin;
import net.curlybois.haven.model.HomelessPerson;
import net.curlybois.haven.model.Shelter;
import net.curlybois.haven.model.ShelterEmployee;
import net.curlybois.haven.model.User;
import net.curlybois.haven.model.database.AppDatabase;

import static net.curlybois.haven.model.User.ADMIN;
import static net.curlybois.haven.model.User.HOMELESS_PERSON;
import static net.curlybois.haven.model.User.SHELTER_EMPLOYEE;

/**
 * Created by jake on 3/31/18.
 */

public class UsersController {

    private static UsersController INSTANCE = null;

    private final AppDatabase db;
    private User currentUser;
    private final SharedPreferences sharedPref;

    /**
     * Get the singleton instance of UsersController
     * @param context the current context
     * @return the singleton instance
     */
    public static UsersController getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new UsersController(context);
        }
        return INSTANCE;
    }

    /**
     * Create a new UsersController
     * @param context the current context
     */
    public UsersController(Context context) {
        this.db = AppDatabase.getAppDatabase(context);
        this.currentUser = null;
        this.sharedPref = context.getSharedPreferences("prefs_users", Context.MODE_PRIVATE);
    }

    /**
     * Get the current logged-in user
     * @return the current logged-in user
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * Logs in a user and sets them as the current user
     *
     * @param email    the user email
     * @param password the user password
     * @return true if login successful, false otherwise
     */
    public boolean login(String email, String password) {
        User user = db.appDao().getUserByEmail(email);
        if ((user == null) || !user.isPasswordCorrect(password)) {
            return false;
        }
        currentUser = user;
        sharedPref.edit()
                .putInt("savedLoginId", user.getId())
                .apply();
        return true;
    }

    /**
     * Logs in a user and sets them as the current user
     *
     * @param id the user id
     * @return true if login successful, false otherwise
     */
    private boolean login(int id) {
        User user = db.appDao().getUserById(id);
        if (user == null) {
            return false;
        }
        currentUser = user;
        sharedPref.edit()
                .putInt("savedLoginId", user.getId())
                .apply();
        return true;
    }

    /**
     * Logs out the current user
     */
    public void logout() {
        if (currentUser == null) {
            return;
        }
        currentUser = null;
        sharedPref.edit()
                .putInt("savedLoginId", -1)
                .apply();
    }

    /**
     * Registers a new user and sets them as the current user
     *
     * @param email    the user email
     * @param password the user password
     * @param type     the account type
     */
    public void register(String email, String password, int type) {
        User user;
        switch (type) {
            case HOMELESS_PERSON:
                user = new HomelessPerson(email, password);
                break;
            case SHELTER_EMPLOYEE:
                user = new ShelterEmployee(email, password);
                break;
            case ADMIN:
                user = new Admin(email, password);
                break;
            default:
                throw new IllegalArgumentException(String.format("Type %d is not allowed", type));
        }
        db.appDao().addUser(user);
        currentUser = user;
        sharedPref.edit()
                .putInt("savedLoginId", user.getId())
                .apply();
    }

    /**
     * Automatically logs in if there is a saved login
     * @return true if we automatically logged in, false if not
     */
    public boolean checkSavedLogin() {
        int savedLoginId = sharedPref.getInt("savedLoginId", -1);
        return (savedLoginId != -1) && login(savedLoginId);
    }

    /**
     * Reserve a number of beds at a shelter
     * @param shelter the shelter to reserve beds at
     * @param num the number of beds to reserve
     * @return true if we successfully reserved beds, false otherwise
     */
    public boolean reserve(Shelter shelter, int num) {
        if ((num > shelter.getCapacity())
                || (currentUser == null)
                || (currentUser.getReservedNum() > 0)) {
            return false;
        }

        currentUser.setReservedShelterId(shelter.getId());
        currentUser.setReservedNum(num);
        return true;
    }

    /**
     * Release the reserved beds at a shelter from reservation
     * @param shelter the shelter to release reservations from
     * @return true if we successfully released beds, false otherwise
     */
    public boolean release(Shelter shelter) {
        if (currentUser == null) {
            return false;
        }

        currentUser.setReservedShelterId(null);
        currentUser.setReservedNum(0);
        return true;
    }

    /**
     * Get the number of reserved beds at a shelter for the current user
     * @param shelter the shelter to check for reserved beds at
     * @return the number of reserved beds
     */
    public int getNumReserved(Shelter shelter) {
        if ((currentUser == null)
                || (currentUser.getReservedShelterId() == null)
                || (currentUser.getReservedShelterId() != shelter.getId())) {
            return 0;
        }
        return currentUser.getReservedNum();
    }
}
