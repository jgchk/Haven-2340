package net.curlybois.haven;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import net.curlybois.haven.model.User;
import net.curlybois.haven.model.database.AppDao;
import net.curlybois.haven.model.database.AppDatabase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by jake on 3/31/18.
 */

@RunWith(AndroidJUnit4.class)
public class AppDatabaseTest {

    // --Commented out by Inspection (4/10/18 6:42 PM):private static final String TAG = AppDatabaseTest.class.getSimpleName();

    private AppDao appDao;
    private AppDatabase mDb;
    private final String email = "test@email.com";
    private final String password = "password";

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getTargetContext();
        mDb = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        appDao = mDb.appDao();
    }

    @After
    public void closeDb() {
        mDb.close();
    }

    /**
     * Jessie
     */
    @Test
    public void getUserByEmail() {
        User user = new User(email, password, User.HOMELESS_PERSON);
        appDao.addUser(user);
        User byEmail = appDao.getUserByEmail(email);
        assertEquals(user, byEmail);
    }

    /**
     * Jessie
     */
    @Test
    public void getUserById() {
        User user = new User(email, password, User.HOMELESS_PERSON);
        appDao.addUser(user);
        user = appDao.getUserByEmail(email);
        User byId = appDao.getUserById(user.getId());
        assertEquals(user, byId);
    }

    /**
     * Liz
     */
    @Test
    public void updateUser() {
        User user = new User(email, password, User.HOMELESS_PERSON);
        appDao.addUser(user);
        user = appDao.getUserByEmail(email);
        String newEmail = "test2@email.com";
        user.setEmail(newEmail);
        appDao.updateUser(user);
        assertEquals(appDao.getUserByEmail(newEmail), user);
    }

    /**
     * Liz
     */
    @Test
    public void removeUser() {
        User user = new User(email, password, User.HOMELESS_PERSON);
        appDao.addUser(user);
        user = appDao.getUserByEmail(email);
        assertNotNull(user);
        appDao.removeUser(user);
        user = appDao.getUserByEmail(email);
        assertNull(user);
    }
}

