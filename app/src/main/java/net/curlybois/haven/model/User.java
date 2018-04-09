package net.curlybois.haven.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by jake on 3/31/18.
 */

@Entity
public class User {

    public static final int HOMELESS_PERSON = 0, SHELTER_EMPLOYEE = 1, ADMIN = 2;

    @PrimaryKey(autoGenerate = true) private int id;
    private String email;
    private String password;
    private boolean locked;
    private int type;
    private Integer reservedShelterId;
    private int reservedNum;

    public User(String email, String password, int type) {
        this.email = email;
        this.password = password;
        this.locked = false;
        this.type = type;
        this.reservedShelterId = null;
        this.reservedNum = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Integer getReservedShelterId() {
        return reservedShelterId;
    }

    public void setReservedShelterId(Integer reservedShelterId) {
        this.reservedShelterId = reservedShelterId;
    }

    public int getReservedNum() {
        return reservedNum;
    }

    public void setReservedNum(int reservedNum) {
        this.reservedNum = reservedNum;
    }

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
        return String.format("User[id=%d, email=%s, password=%s, locked=%s, type=%d]", id, email, password, locked, type);
    }
}
