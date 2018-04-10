package net.curlybois.haven.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import net.curlybois.haven.R;
import net.curlybois.haven.controllers.UsersController;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Registers a new user account
 */
public class RegistrationActivity extends AppCompatActivity {

    private static final String STATE_EMAIL = "email";
    private static final String STATE_PASSWORD = "password";
    private static final String STATE_ACCOUNT_TYPE = "accountType";

    @BindView(R.id.account_type_spn) private Spinner accountType_spn;
    @BindView(R.id.email_txe) private TextInputEditText email_txe;
    @BindView(R.id.password_txe) private TextInputEditText password_txe;
    @BindView(R.id.register_btn) private Button register_btn;
    @BindView(R.id.login_txv) private TextView login_txv;

    private UsersController usersController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Views setup
        setContentView(R.layout.activity_registration);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

        // Account type spinner setup
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.account_types_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        accountType_spn.setAdapter(adapter);

        // OnClick listeners
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
        login_txv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLoginActivity();
            }
        });

        // Restore state
        if (savedInstanceState != null) {
            setEmail(savedInstanceState.getString(STATE_EMAIL));
            setPassword(savedInstanceState.getString(STATE_PASSWORD));
            setAccountType(savedInstanceState.getInt(STATE_ACCOUNT_TYPE));
        }

        // Initialize controller
        usersController = UsersController.getInstance(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(STATE_EMAIL, getEmail());
        outState.putString(STATE_PASSWORD, getPassword());
        outState.putInt(STATE_ACCOUNT_TYPE, getAccountType());
    }

    /**
     * Attempts to register a new account and log in using the entered email, password,
     * and account type
     */
    private void register() {
        usersController.register(getEmail(), getPassword(), getAccountType());
        openMainActivity();
    }

    private void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void openLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
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

    private int getAccountType() {
        return accountType_spn.getSelectedItemPosition();
    }

    private void setAccountType(int type) {
        accountType_spn.setSelection(type);
    }
}
