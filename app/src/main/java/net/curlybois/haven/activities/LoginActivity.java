package net.curlybois.haven.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import net.curlybois.haven.R;
import net.curlybois.haven.controllers.UsersController;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Logs in the user
 */
public class LoginActivity extends AppCompatActivity {
    private static final String STATE_EMAIL = "email";
    private static final String STATE_PASSWORD = "password";

    @BindView(R.id.email_txe) TextInputEditText email_txe;
    @BindView(R.id.password_txe) TextInputEditText password_txe;
    @BindView(R.id.login_btn) Button login_btn;
    @BindView(R.id.register_txv) TextView register_txv;

    private UsersController usersController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize controller
        usersController = UsersController.getInstance(this);

        // Automatically log in if there is a saved login
        if (usersController.checkSavedLogin()) {
            openMainActivity();
        }

        // Views setup
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        // OnClick listeners
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        register_txv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRegistrationActivity();
            }
        });

        // Restore state
        if (savedInstanceState != null) {
            setEmail(savedInstanceState.getString(STATE_EMAIL));
            setPassword(savedInstanceState.getString(STATE_PASSWORD));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(STATE_EMAIL, getEmail());
        outState.putString(STATE_PASSWORD, getPassword());
    }

    /**
     * Attempts to log in using the entered email and password
     */
    private void login() {
        boolean loginSuccess = usersController.login(getEmail(), getPassword());
        if (loginSuccess) {
            openMainActivity();
        } else {
            password_txe.setError("Invalid login");
            password_txe.requestFocus();
        }
    }

    private void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void openRegistrationActivity() {
        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);
    }

    private String getEmail() {
        return email_txe.getText().toString();
    }

    private void setEmail(CharSequence email) {
        email_txe.setText(email);
    }

    private String getPassword() {
        return password_txe.getText().toString();
    }

    private void setPassword(CharSequence password) {
        password_txe.setText(password);
    }
}
