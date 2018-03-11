package net.curlybois.haven.controller;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

import net.curlybois.haven.R;
import net.curlybois.haven.TempDatabase;
import net.curlybois.haven.model.Admin;
import net.curlybois.haven.model.HomelessPerson;
import net.curlybois.haven.model.ShelterEmployee;
import net.curlybois.haven.model.User;

public class RegistrationActivity extends AppCompatActivity {

    private FirebaseAuth auth = FirebaseAuth.getInstance();

    private TextInputEditText emailInput, passwordInput;
    private Spinner accountPicker;
    private Button registerBtn;
    private TextView loginBtn;
    private ProgressBar registrationProgress;

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
            auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            showProgress(false);
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                                startActivity(intent);
                            } else {
                                if (task.getException() instanceof FirebaseAuthWeakPasswordException) {
                                    passwordInput.setError(getString(R.string.error_invalid_password));
                                    passwordInput.requestFocus();
                                } else if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                    emailInput.setError(getString(R.string.error_invalid_email));
                                    emailInput.requestFocus();
                                } else if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                    emailInput.setError(getString(R.string.error_email_exists));
                                    emailInput.requestFocus();
                                } else {
                                    Snackbar.make(findViewById(android.R.id.content),
                                            "Authentication failed.",
                                            Snackbar.LENGTH_SHORT)
                                            .show();
                                }
                            }
                        }
                    });
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
}
