package com.fuelstationmanagerapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.widget.NestedScrollView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.fuelstationmanagerapp.helpers.InputValidation;
import com.fuelstationmanagerapp.model.User;
import com.fuelstationmanagerapp.sql.DatabaseHelper;
import com.google.android.material.snackbar.Snackbar;
import com.sdsmdg.tastytoast.TastyToast;

public class RegisterScreen extends AppCompatActivity implements View.OnClickListener{

    private final AppCompatActivity activity = RegisterScreen.this;

    // creating constant keys for shared preferences.
    public static final String SHARED_PREFS = "shared_prefs";

    // key for storing email.
    public static final String EMAIL_KEY = "email_key";

    // key for storing password.
    public static final String PASSWORD_KEY = "password_key";

    // key for storing user name.
    public static final String NAME_KEY = "name_key";

    // variable for shared preferences.
    SharedPreferences sharedpreferences;
    String email, password, name;

    private TextView nameErrors;
    private TextView emailErrors;
    private TextView passwordErrors;
    private TextView confPasswordErrors;

    private EditText editTextName;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextConfirmPassword;

    private RadioGroup rg;

    private Button buttonRegister;
    private TextView goToLogin;
    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;
    private User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen);
        getSupportActionBar().hide();

        initViews();
        initListeners();
        initObjects();
    }

    /**
     * This method is to initialize views
     */
    private void initViews() {
        nameErrors = (TextView) findViewById(R.id.nameErrors);
        emailErrors = (TextView) findViewById(R.id.emailErrors);
        passwordErrors = (TextView) findViewById(R.id.passwordErrors);
        confPasswordErrors = (TextView) findViewById(R.id.confPasswordErrors);

        nameErrors.setVisibility(View.GONE);
        emailErrors.setVisibility(View.GONE);
        passwordErrors.setVisibility(View.GONE);
        confPasswordErrors.setVisibility(View.GONE);


        editTextName = (EditText) findViewById(R.id.inputName);
        editTextEmail = (EditText) findViewById(R.id.inputEmail);
        editTextPassword = (EditText) findViewById(R.id.inputPassword);
        editTextConfirmPassword = (EditText) findViewById(R.id.confirmPassword);

        rg = (RadioGroup) findViewById(R.id.radioGroup);

        buttonRegister = (Button) findViewById(R.id.btnRegister);
        goToLogin = (TextView) findViewById(R.id.gotoLogin);

        // getting the data which is stored in shared preferences.
        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

        // default value is set to null if not present.
        email = sharedpreferences.getString(EMAIL_KEY, null);
        password = sharedpreferences.getString(PASSWORD_KEY, null);
        name = sharedpreferences.getString(NAME_KEY, null);

    }

    /**
     * This method is to initialize listeners
     */
    private void initListeners() {
        buttonRegister.setOnClickListener(this);
        goToLogin.setOnClickListener(this);
    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        inputValidation = new InputValidation(activity);
        databaseHelper = new DatabaseHelper(activity);
        user = new User();
    }

    /**
     * This implemented method is to listen the click on view
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnRegister:
                postDataToSQLite();
                break;
            case R.id.gotoLogin:
                finish();
                break;
        }
    }

    /**
     * This method is to validate the input text fields and post data to SQLite
     */
    private void postDataToSQLite() {
        if (!inputValidation.isInputEditTextFilled(editTextName, nameErrors, getString(R.string.name_empty))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(editTextEmail, emailErrors, getString(R.string.email_empty))) {
            return;
        }
        if (!inputValidation.isInputEditTextEmail(editTextEmail, emailErrors, getString(R.string.invalid_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(editTextPassword, passwordErrors, getString(R.string.pwd_empty))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(editTextConfirmPassword, confPasswordErrors, getString(R.string.conf_pwd_empty))) {
            return;
        }

        if (!inputValidation.isInputEditTextMatches(editTextPassword, editTextConfirmPassword,
                confPasswordErrors, getString(R.string.error_password_match))) {
            return;
        }

        if (rg.getCheckedRadioButtonId() == -1)
        {
            TastyToast.makeText(getApplicationContext(), "Please select a user type!", TastyToast.LENGTH_LONG, TastyToast.ERROR);
            return;
        }

        if (!databaseHelper.checkUser(editTextEmail.getText().toString().trim())) {

            user.setName(editTextName.getText().toString().trim());
            user.setEmail(editTextEmail.getText().toString().trim());
            user.setPassword(editTextPassword.getText().toString().trim());
            user.setRole(((RadioButton)findViewById(rg.getCheckedRadioButtonId())).getText().toString());
            databaseHelper.addUser(user);
            SharedPreferences.Editor editor = sharedpreferences.edit();

            // store email and password in shared preferences.
            editor.putString(EMAIL_KEY, editTextEmail.getText().toString());
            editor.putString(PASSWORD_KEY, editTextPassword.getText().toString());
            editor.putString(NAME_KEY, editTextName.getText().toString());

            // to save data with key and value.
            editor.apply();

            Intent mainIntent = new Intent(activity, MainActivity.class);
            TastyToast.makeText(getApplicationContext(), "User registered successfully", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
            emptyInputEditText();
            startActivity(mainIntent);

        } else {
            TastyToast.makeText(getApplicationContext(), "User already exists with same email", TastyToast.LENGTH_LONG, TastyToast.ERROR);
        }
    }
        /**
         * This method is to empty all input edit text
         */
        private void emptyInputEditText() {
            editTextEmail.setText(null);
            editTextPassword.setText(null);
            editTextConfirmPassword.setText(null);
        }
    }


