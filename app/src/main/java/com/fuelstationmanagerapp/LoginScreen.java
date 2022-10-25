package com.fuelstationmanagerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.fuelstationmanagerapp.helpers.InputValidation;
import com.fuelstationmanagerapp.model.User;
import com.fuelstationmanagerapp.sql.DatabaseHelper;

public class LoginScreen extends AppCompatActivity implements View.OnClickListener{

    private final AppCompatActivity activity = LoginScreen.this;

    // creating constant keys for shared preferences.
    public static final String SHARED_PREFS = "shared_prefs";

    // key for storing email.
    public static final String EMAIL_KEY = "email_key";

    // key for storing password.
    public static final String PASSWORD_KEY = "password_key";

    // variable for shared preferences.
    SharedPreferences sharedpreferences;
    String email, password;

    private TextView emailErrors;
    private TextView passwordErrors;

    private EditText editTextEmail;
    private EditText editTextPassword;

    private Button buttonLogin;
    private TextView goToRegister;
    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        getSupportActionBar().hide();

        initViews();
        initListeners();
        initObjects();
    }

    /**
     * This method is to initialize views
     */
    private void initViews() {
        emailErrors = (TextView) findViewById(R.id.emailErrors);
        passwordErrors = (TextView) findViewById(R.id.passwordErrors);

        emailErrors.setVisibility(View.GONE);
        passwordErrors.setVisibility(View.GONE);

        editTextEmail = (EditText) findViewById(R.id.inputEmail);
        editTextPassword = (EditText) findViewById(R.id.inputPassword);

        buttonLogin = (Button) findViewById(R.id.btnLogin);
        goToRegister = (TextView) findViewById(R.id.gotoRegister);

        // getting the data which is stored in shared preferences.
        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

        // default value is set to null if not present.
        email = sharedpreferences.getString("EMAIL_KEY", null);
        password = sharedpreferences.getString("PASSWORD_KEY", null);
    }

    /**
     * This method is to initialize listeners
     */
    private void initListeners() {
        buttonLogin.setOnClickListener(this);
        goToRegister.setOnClickListener(this);
    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        databaseHelper = new DatabaseHelper(activity);
        inputValidation = new InputValidation(activity);
    }

    /**
     * This implemented method is to listen the click on view
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin:
                verifyFromSQLite();
                break;
            case R.id.gotoRegister:
                // Navigate to RegisterActivity
                Intent intentRegister = new Intent(getApplicationContext(), RegisterScreen.class);
                startActivity(intentRegister);
                break;
        }
    }

    /**
     * This method is to validate the input text fields and verify login credentials from SQLite
     */
    private void verifyFromSQLite() {
        if (!inputValidation.isInputEditTextFilled(editTextEmail, emailErrors, getString(R.string.email_empty))) {
            return;
        }
        if (!inputValidation.isInputEditTextEmail(editTextEmail, emailErrors, getString(R.string.invalid_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(editTextPassword, passwordErrors, getString(R.string.pwd_empty))) {
            return;
        }
        if (databaseHelper.checkUser(editTextEmail.getText().toString().trim()
                , editTextPassword.getText().toString().trim())) {
            SharedPreferences.Editor editor = sharedpreferences.edit();

            // store email and password in shared preferences.
            editor.putString(EMAIL_KEY, editTextEmail.getText().toString());
            editor.putString(PASSWORD_KEY, editTextPassword.getText().toString());

            // to save data with key and value.
            editor.apply();

            Intent mainIntent = new Intent(activity, MainActivity.class);
            mainIntent.putExtra("EMAIL", editTextEmail.getText().toString().trim());
            emptyInputEditText();
            Toast.makeText( getBaseContext(), "User login success!",Toast.LENGTH_LONG).show();
            startActivity(mainIntent);
        } else {
            // Snack Bar to show success message that record is wrong
//            Snackbar.make(nestedScrollView, getString(R.string.error_valid_email_password), Snackbar.LENGTH_LONG).show();
            Toast.makeText( getBaseContext(), "User login failed!",Toast.LENGTH_LONG).show();

        }
    }

    /**
     * This method is to empty all input edit text
     */
    private void emptyInputEditText() {
        editTextEmail.setText(null);
        editTextPassword.setText(null);
    }
}