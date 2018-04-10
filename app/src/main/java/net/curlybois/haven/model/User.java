package net.curlybois.haven.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by jake on 3/31/18.
 */

@Entity
public class User {

    public static final int HOMELESS_PERSON = 0;
    public static final int SHELTER_EMPLOYEE = 1;
    public static final int ADMIN = 2;

    @PrimaryKey(autoGenerate = true) private int id;
    private String email;
    private final String password;
    private boolean locked;
    private final int type;
    private Integer reservedShelterId;
    private int reservedNum;

    /**
     * Create a new user
     * @param email the user email
     * @param password the user password
     * @param type the type of user
     */
    public User(String email, String password, int type) {
        this.email = email;
        this.password = password;
        this.locked = false;
        this.type = type;
        this.reservedShelterId = null;
        this.reservedNum = 0;
    }

    /**
     * Get the user id
     * @return the user id
     */
    public int getId() {
        return id;
    }

    /**
     * Set the user id
     * @param id the user id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Get the user email
     * @return the user email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set the user email
     * @param email the user email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Get the user password
     * @return the user password
     */
    public String getPassword() {
        return password;
    }

// --Commented out by Inspection START (4/10/18 6:42 PM):
//    public void setPassword(String password) {
//        this.password = password;
//    }
// --Commented out by Inspection STOP (4/10/18 6:42 PM)

    /**
     * Get whether the user is locked or not
     * @return true if the user is locked, false otherwise
     */
    public boolean isLocked() {
        return locked;
    }

    /**
     * Set whether the user is locked or not
     * @param locked whether the user is locked
     */
    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    /**
     * Get the user type
     * @return the user type
     */
    public int getType() {
        return type;
    }

// --Commented out by Inspection START (4/10/18 6:42 PM):
//    public void setType(int type) {
//        this.type = type;
//    }
// --Commented out by Inspection STOP (4/10/18 6:42 PM)

    /**
     * Get the id of the shelter where the user has beds reserved at
     * @return the shelter id
     */
    public Integer getReservedShelterId() {
        return reservedShelterId;
    }

    /**
     * Set the id of the shelter where the user has beds reserved at
     * @param reservedShelterId the shelter id
     */
    public void setReservedShelterId(Integer reservedShelterId) {
        this.reservedShelterId = reservedShelterId;
    }

    /**
     * Get the number of reserved beds
     * @return the number of reserved beds
     */
    public int getReservedNum() {
        return reservedNum;
    }

    /**
     * Set the number of reserved beds
     * @param reservedNum the number of reserved beds
     */
    public void setReservedNum(int reservedNum) {
        this.reservedNum = reservedNum;
    }

    /**
     * Check whether a password matches the user password
     * @param password the password to check
     * @return true if matched, false otherwise
     */
    public boolean isPasswordCorrect(String password) {
        return this.password.equals(password);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof User) {
            User other = (User) obj;
            return other.email.equals(email) && other.password.equals(password);
        }
        return false;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", locked=" + locked +
                ", type=" + type +
                ", reservedShelterId=" + reservedShelterId +
                ", reservedNum=" + reservedNum +
                '}';
    }
}
