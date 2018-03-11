package net.curlybois.haven.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

import net.curlybois.haven.R;
import net.curlybois.haven.model.User;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth auth = FirebaseAuth.getInstance();

    private TextInputEditText emailInput, passwordInput;
    private Button loginBtn;
    private TextView registerBtn;
    private ProgressBar loginProgress;

//    /**
//     * The current async authorization task
//     */
//    private UserLoginTask authTask = null;

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

    @Override
    protected void onResume() {
        super.onResume();

        clearErrors();
        emailInput.setText("");
        passwordInput.setText("");
        emailInput.requestFocus();
    }

    private void clearErrors() {
        emailInput.setError(null);
        passwordInput.setError(null);
    }

    /**
     * Try to log in using whatever is typed into the login boxes
     */
    private void attemptLogin() {
        // Don't try to login if we are already doing so
//        if (authTask != null) {
//            return;
//        }

        // Get the input username and password
        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();

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
            clearErrors();
            auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            showProgress(false);
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                            } else {
                                if (task.getException() instanceof FirebaseAuthInvalidUserException) {
                                    emailInput.setError(getString(R.string.error_invalid_email));
                                    emailInput.requestFocus();
                                } else if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                    passwordInput.setError(getString(R.string.error_incorrect_password));
                                    passwordInput.requestFocus();
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
     * Show or hide the login progress bar
     *
     * @param show whether to show the progress bar
     */
    private void showProgress(final boolean show) {
        loginProgress.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
        loginBtn.setEnabled(!show);
    }
}
