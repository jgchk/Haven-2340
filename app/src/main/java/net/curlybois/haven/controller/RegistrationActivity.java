package net.curlybois.haven.controller;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import net.curlybois.haven.R;
import net.curlybois.haven.TempDatabase;
import net.curlybois.haven.model.Admin;
import net.curlybois.haven.model.HomelessPerson;
import net.curlybois.haven.model.ShelterEmployee;
import net.curlybois.haven.model.User;

public class RegistrationActivity extends AppCompatActivity {

    private TextInputEditText emailInput, passwordInput;
    private Spinner accountPicker;
    private Button registerBtn;
    private TextView loginBtn;
    private ProgressBar registrationProgress;

    /**
     * The current async authorization task
     */
    private UserRegistrationTask authTask = null;

    /**
     * Various possible outcomes of a registration trial
     */
    private enum RegistrationStatus {
        SUCCESSFUL,
        INVALID_USER_TYPE,
        EMAIL_ALREADY_EXISTS,
        NETWORK_FAILURE
    }

    private static final int HOMELESS_PERSON = 0, SHELTER_EMPLOYEE = 1, ADMIN = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        initView();
    }

    private void initView() {
        accountPicker = findViewById(R.id.account_type_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.account_types_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        accountPicker.setAdapter(adapter);

        emailInput = findViewById(R.id.email_input);
        passwordInput = findViewById(R.id.password_input);
        registerBtn = findViewById(R.id.register_btn);
        loginBtn = findViewById(R.id.login_btn);
        registrationProgress = findViewById(R.id.registration_progress);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptRegistration();
            }
        });
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        emailInput.setText("");
        emailInput.requestFocus();
        emailInput.setError(null);
        passwordInput.setText("");
        passwordInput.setError(null);
        accountPicker.setSelection(0);
    }

    private void attemptRegistration() {
        // Don't try to register if we are already doing so
        if (authTask != null) {
            return;
        }

        // Get the input username and password
        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();
        int userType = accountPicker.getSelectedItemPosition();

        boolean cancel = false; // Keep track of whether we should cancel due to error
        View focusView = null; // The view to focus on if we get an error

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !User.isPasswordValid(password)) {
            passwordInput.setError(getString(R.string.error_invalid_password));
            focusView = passwordInput;
            cancel = true;
        }

        // Check for a valid username.
        if (TextUtils.isEmpty(email)) {
            emailInput.setError(getString(R.string.error_field_required));
            focusView = emailInput;
            cancel = true;
        } else if (!User.isUsernameValid(email)) {
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
            authTask = new UserRegistrationTask(email, password, userType);
            authTask.execute((Void) null);
        }
    }

    /**
     * Show or hide the registration progress bar
     *
     * @param show whether to show the progress bar
     */
    private void showProgress(final boolean show) {
        registrationProgress.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
        loginBtn.setEnabled(!show);
    }

    /**
     * An asynchronous login task
     */
    @SuppressLint("StaticFieldLeak")
    public class UserRegistrationTask extends AsyncTask<Void, Void, RegistrationStatus> {

        private final String email;
        private final String password;
        private final int userType;

        UserRegistrationTask(String email, String password, int userType) {
            this.email = email;
            this.password = password;
            this.userType = userType;
        }

        @Override
        protected RegistrationStatus doInBackground(Void... params) {
//            try {
//                // Simulate network access.
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                return RegistrationStatus.NETWORK_FAILURE;
//            }

            User user;
            switch (userType) {
                case HOMELESS_PERSON:
                    user = new HomelessPerson(email, password);
                    break;
                case SHELTER_EMPLOYEE:
                    user = new ShelterEmployee(email, password);
                    break;
                case ADMIN:
                    user = new Admin(email, password);
                    break;
                default:
                    return RegistrationStatus.INVALID_USER_TYPE;
            }
            if (TempDatabase.addUser(user)) {
                return RegistrationStatus.SUCCESSFUL;
            }
            return RegistrationStatus.EMAIL_ALREADY_EXISTS;
        }

        @Override
        protected void onPostExecute(final RegistrationStatus registrationStatus) {
            authTask = null;
            showProgress(false);

            switch (registrationStatus) {
                case SUCCESSFUL:
                    Intent intent = new Intent(RegistrationActivity.this, ShelterListActivity.class);
                    startActivity(intent);
                    break;
                case INVALID_USER_TYPE:
                    Snackbar.make(findViewById(android.R.id.content),
                            "Invalid user type.",
                            Snackbar.LENGTH_SHORT)
                            .show();
                    accountPicker.requestFocus();
                    break;
                case EMAIL_ALREADY_EXISTS:
                    emailInput.setError("Email already exists");
                    emailInput.requestFocus();
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
