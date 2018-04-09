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

    private static final String TAG = UsersController.class.getSimpleName();
    private static UsersController INSTANCE = null;

    private AppDatabase db;
    private User currentUser;
    private SharedPreferences sharedPref;

    public static UsersController getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new UsersController(context);
        }
        return INSTANCE;
    }

    public UsersController(Context context) {
        this.db = AppDatabase.getAppDatabase(context);
        this.currentUser = null;
        this.sharedPref = context.getSharedPreferences("prefs_users", Context.MODE_PRIVATE);
    }

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
        if (user == null || !user.isPasswordCorrect(password)) {
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
    public boolean login(int id) {
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
     *
     * @return true if the logout was successful, false if there is no user to log out
     */
    public boolean logout() {
        if (currentUser == null) {
            return false;
        }
        currentUser = null;
        sharedPref.edit()
                .putInt("savedLoginId", -1)
                .apply();
        return true;
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
        if (type == HOMELESS_PERSON) {
            user = new HomelessPerson(email, password);
        } else if (type == SHELTER_EMPLOYEE) {
            user = new ShelterEmployee(email, password);
        } else if (type == ADMIN) {
            user = new Admin(email, password);
        } else {
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
     */
    public boolean checkSavedLogin() {
        int savedLoginId = sharedPref.getInt("savedLoginId", -1);
        return savedLoginId != -1 && login(savedLoginId);
    }

    public boolean reserve(Shelter shelter, int num) {
        if (num > shelter.getCapacity() || currentUser == null || currentUser.getReservedNum() > 0) {
            return false;
        }

        currentUser.setReservedShelterId(shelter.getId());
        currentUser.setReservedNum(num);
        return true;
    }

    public boolean release(Shelter shelter) {
        if (currentUser == null) {
            return false;
        }

        currentUser.setReservedShelterId(null);
        currentUser.setReservedNum(0);
        return true;
    }

    public int getNumReserved(Shelter shelter) {
        if (currentUser == null || currentUser.getReservedShelterId() == null || currentUser.getReservedShelterId() != shelter.getId()) {
            return 0;
        }
        return currentUser.getReservedNum();
    }
}
