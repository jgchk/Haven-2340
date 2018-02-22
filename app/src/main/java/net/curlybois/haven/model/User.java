package net.curlybois.haven.model;

/**
 * Created by jake on 2/20/2018.
 */

public class User {
    private String email;
    private String password;
    private boolean locked;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
        this.locked = false;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public boolean isLocked() {
        return locked;
    }

    /**
     * Check whether the inputted username fits username requirements
     *
     * @param username the username to check
     * @return whether the username is valid
     */
    public static boolean isUsernameValid(String username) {
        //TODO: Replace with requirements from future assignments
        return true;
    }

    /**
     * Check whether the inputted password fits password requirements
     *
     * @param password the password to check
     * @return whether the password is valid
     */
    public static boolean isPasswordValid(String password) {
        //TODO: Replace with requirements from future assignments
        return true;
    }
}
