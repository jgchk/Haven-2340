package net.curlybois.haven;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

public class RegistrationActivity extends AppCompatActivity {

    private TextInputEditText emailInput, passwordInput;
    private Spinner accountPicker;
    private Button registerBtn;
    private TextView loginBtn;
    private ProgressBar loginProgress;

    /**
     * Various possible outcomes of a login trial
     */
    private enum RegistrationStatus {
        SUCCESSFUL,
        NETWORK_FAILURE
    }

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
        loginProgress = findViewById(R.id.login_progress);
    }

    /**
     * An asynchronous login task
     */
    @SuppressLint("StaticFieldLeak")
    public class UserRegistrationTask extends AsyncTask<Void, Void, RegistrationStatus> {

        private final String email;
        private final String password;

        UserRegistrationTask(String email, String password) {
            this.email = email;
            this.password = password;
        }

        @Override
        protected RegistrationStatus doInBackground(Void... params) {
//            try {
//                // Simulate network access.
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                return RegistrationStatus.NETWORK_FAILURE;
//            }

            for (String credential : DUMMY_CREDENTIALS) {
                String[] pieces = credential.split(":");
                if (pieces[0].equals(email) && pieces[1].equals(password)) {
                    return RegistrationStatus.SUCCESSFUL;
                }
            }

            return RegistrationStatus.INVALID_LOGIN;
        }

        @Override
        protected void onPostExecute(final LoginActivity.LoginStatus loginStatus) {
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
