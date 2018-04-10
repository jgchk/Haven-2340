package net.curlybois.haven;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import net.curlybois.haven.controllers.UsersController;
import net.curlybois.haven.model.User;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import static net.curlybois.haven.model.User.HOMELESS_PERSON;

/**
 * Created by jake on 3/31/18.
 */

@RunWith(AndroidJUnit4.class)
public class UsersControllerTest {

    private UsersController usersController;
    private final String email = "test@email.com";
    private final String password = "password";

    @Before
    public void setUp() {
        Context context = InstrumentationRegistry.getTargetContext();
        assertNotNull(context);
        usersController = new UsersController(context);
    }

    /**
     * Grant
     */
    @Test
    public void login() {
        // I can log in to an account I just made
        usersController.register(email, password, HOMELESS_PERSON);
        boolean loginSuccess = usersController.login(email, password);
        assertTrue(loginSuccess);

        // The current user is set correctly after logging in
        User user = usersController.getCurrentUser();
        assertNotNull(user);
        assertEquals(user.getEmail(), email);
        assertEquals(user.getPassword(), password);

        // Logging in with the wrong email fails
        loginSuccess = usersController.login("fail", password);
        assertFalse(loginSuccess);
        assertEquals(user, usersController.getCurrentUser());

        // Logging in with the wrong password fails
        loginSuccess = usersController.login(email, "fail");
        assertFalse(loginSuccess);
        assertEquals(user, usersController.getCurrentUser());

        // Logging into another account correctly resets the current user
        usersController.register("test2@email.com", password, HOMELESS_PERSON);
        loginSuccess = usersController.login("test2@email.com", password);
        assertTrue(loginSuccess);
        assertFalse(user.equals(usersController.getCurrentUser()));
    }

    /**
     * Anna
     */
    @Test
    public void currentUser() {
        usersController.logout();
        assertNull(usersController.getCurrentUser());
        usersController.login(email, password);
        User currentUser = usersController.getCurrentUser();
        assertEquals(email, currentUser.getEmail());
        assertEquals(password, currentUser.getPassword());
    }
}
