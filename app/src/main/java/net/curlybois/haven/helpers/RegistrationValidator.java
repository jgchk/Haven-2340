package net.curlybois.haven.helpers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrationValidator {

    /**
     * Checks whether an email is in a valid format
     * @param email the email to check
     * @return whether the email is valid
     */
    private boolean isEmailValid(String email) {
        Pattern p = Pattern.compile("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])");
        Matcher m = p.matcher(email);
        return m.find();
    }

    /**
     * Checks whether a password matches the following criteria:
     *  - Length 8
     * @param password the password to check
     * @return whether the password matches the criteria
     */
    private boolean isPasswordValid(String password) {
        Pattern p = Pattern.compile("[0-9a-zA-Z!@#$%]{8,}");
        Matcher m = p.matcher(password);
        return m.find();
    }
}
