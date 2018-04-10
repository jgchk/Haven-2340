package net.curlybois.haven.model.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import net.curlybois.haven.model.Shelter;
import net.curlybois.haven.model.User;

import java.util.List;

/**
 * Created by jake on 3/31/18.
 */

@Dao
public interface AppDao {

// --Commented out by Inspection START (4/10/18 6:42 PM):
//    // USERS
//    @Query("SELECT * FROM user")
//    List<User> getUsers();
// --Commented out by Inspection STOP (4/10/18 6:42 PM)

    /**
     * Find a user by their email address
     * @param email the email address
     * @return the user with that email
     */
    @Query("SELECT * FROM user WHERE email = :email")
    User getUserByEmail(String email);

    /**
     * Find a user by their id
     * @param id the id
     * @return the user with that id
     */
    @Query("SELECT * FROM user WHERE id = :id")
    User getUserById(int id);

    /**
     * Add a new user
     * @param user the new user
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void addUser(User user);

    /**
     * Update a user's information
     * @param user the user
     */
    @Update
    void updateUser(User user);

    /**
     * Remove a user
     * @param user the user
     */
    @Delete
    void removeUser(User user);


    // SHELTERS

    /**
     * Get all shelters
     * @return the list of shelters
     */
    @Query("SELECT * FROM shelter")
    List<Shelter> getShelters();

// --Commented out by Inspection START (4/10/18 6:42 PM):
//    @Insert
//    void addShelters(List<Shelter> shelters);
// --Commented out by Inspection STOP (4/10/18 6:42 PM)

    /**
     * Update a shelter's information
     * @param shelter the shelter
     */
    @Update
    void updateShelter(Shelter shelter);
}
