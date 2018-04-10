package net.curlybois.haven;

import net.curlybois.haven.model.Admin;
import net.curlybois.haven.model.HomelessPerson;
import net.curlybois.haven.model.User;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by jake on 3/31/18.
 */

public class UserTest {

    /**
     * Jake
     */
    @Test
    public void equals_isCorrect() {
        String email = "test@email.com";
        String password = "password";
        User user1 = new User(email, password, User.HOMELESS_PERSON);
        User user2 = new User(email, password, User.HOMELESS_PERSON);
        assertTrue(user1.equals(user2));

        String email2 = "test2@email.com";
        user2 = new User(email2, password, User.HOMELESS_PERSON);
        assertFalse(user1.equals(user2));

        String password2 = "password2";
        user2 = new User(email, password2, User.HOMELESS_PERSON);
        assertFalse(user1.equals(user2));

        user2 = new HomelessPerson(email, password);
        assertTrue(user1.equals(user2));

        user1 = new Admin(email, password);
        assertTrue(user1.equals(user2));
    }
}
