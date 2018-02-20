package net.curlybois.haven;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText emailInput, passwordInput;
    private Button loginBtn;
    private TextView registerBtn;
    private ProgressBar loginProgress;

    /**
     * Dummy user credentials for use in M4
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "user:pass"
    };

    /**
     * The current async authorization task
     */
    private UserLoginTask authTask = null;

    /**
     * Various possible outcomes of a login trial
     */
    private enum LoginStatus {
        SUCCESSFUL,
        NETWORK_FAILURE,
        INVALID_LOGIN
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
    }

    /**
     * Initialize variables holding views and setup click handlers
     */
    private void initView() {
        emailInput = findViewById(R.id.email_input);
        passwordInput = findViewById(R.id.password_input);
        loginBtn = findViewById(R.id.login_btn);
        registerBtn = findViewById(R.id.register_btn);
        loginProgress = findViewById(R.id.login_progress);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Try to log in using whatever is typed into the login boxes
     */
    private void attemptLogin() {
        // Don't try to login if we are already doing so
        if (authTask != null) {
            return;
        }

        // Get the inputted username and password
        String username = emailInput.getText().toString();
        String password = passwordInput.getText().toString();

        boolean cancel = false; // Keep track of whether we should cancel due to error
        View focusView = null; // The view to focus on if we get an error

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            passwordInput.setError(getString(R.string.error_invalid_password));
            focusView = passwordInput;
            cancel = true;
        }

        // Check for a valid username.
        if (TextUtils.isEmpty(username)) {
            emailInput.setError(getString(R.string.error_field_required));
            focusView = emailInput;
            cancel = true;
        } else if (!isUsernameValid(username)) {
            emailInput.setError(getString(R.string.error_invalid_email));
            focusView = emailInput;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // If everything went well, let's try to log in
            showProgress(true);
            authTask = new UserLoginTask(username, password);
            authTask.execute((Void) null);
        }
    }

    /**
     * Check whether the inputted username fits username requirements
     *
     * @param username the username to check
     * @return whether the username is valid
     */
    private boolean isUsernameValid(String username) {
        //TODO: Replace with requirements from future assignments
        return true;
    }

    /**
     * Check whether the inputted password fits password requirements
     *
     * @param password the password to check
     * @return whether the password is valid
     */
    private boolean isPasswordValid(String password) {
        //TODO: Replace with requirements from future assignments
        return true;
    }

    /**
     * Show or hide the login progress bar
     *
     * @param show whether to show the progress bar
     */
    private void showProgress(final boolean show) {
        loginProgress.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
        loginBtn.setEnabled(!show);
    }

    /**
     * An asynchronous login task
     */
    @SuppressLint("StaticFieldLeak")
    public class UserLoginTask extends AsyncTask<Void, Void, LoginStatus> {

        private final String email;
        private final String password;

        UserLoginTask(String email, String password) {
            this.email = email;
            this.password = password;
        }

        @Override
        protected LoginStatus doInBackground(Void... params) {
//            try {
//                // Simulate network access.
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                return LoginStatus.NETWORK_FAILURE;
//            }

            for (String credential : DUMMY_CREDENTIALS) {
                String[] pieces = credential.split(":");
                if (pieces[0].equals(email) && pieces[1].equals(password)) {
                    return LoginStatus.SUCCESSFUL;
                }
            }

            return LoginStatus.INVALID_LOGIN;
        }

        @Override
        protected void onPostExecute(final LoginStatus loginStatus) {
            authTask = null;
            showProgress(false);

            switch (loginStatus) {
                case SUCCESSFUL:
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    break;
                case INVALID_LOGIN:
                    passwordInput.setError(getString(R.string.error_incorrect_password));
                    passwordInput.requestFocus();
                    break;
                case NETWORK_FAILURE:
                    Snackbar.make(findViewById(android.R.id.content),
                            "Network error. Try logging in again.",
                            Snackbar.LENGTH_SHORT)
                            .show();
                    break;
            }
        }

        @Override
        protected void onCancelled() {
            authTask = null;
            showProgress(false);
        }
    }
}
