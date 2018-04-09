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

    // USERS
    @Query("SELECT * FROM user")
    List<User> getUsers();

    @Query("SELECT * FROM user WHERE email = :email")
    User getUserByEmail(String email);

    @Query("SELECT * FROM user WHERE id = :id")
    User getUserById(int id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void addUser(User user);

    @Update
    void updateUser(User user);

    @Delete
    void removeUser(User user);


    // SHELTERS
    @Query("SELECT * FROM shelter")
    List<Shelter> getShelters();

    @Insert
    void addShelters(List<Shelter> shelters);

    @Update
    void updateShelter(Shelter shelter);
}
