package com.fuelstationmanagerapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.widget.NestedScrollView;

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

public class RegisterScreen extends AppCompatActivity implements View.OnClickListener{

    private final AppCompatActivity activity = RegisterScreen.this;

    private TextView emailErrors;
    private TextView passwordErrors;
    private TextView confPasswordErrors;

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
        emailErrors = (TextView) findViewById(R.id.emailErrors);
        passwordErrors = (TextView) findViewById(R.id.passwordErrors);
        confPasswordErrors = (TextView) findViewById(R.id.confPasswordErrors);

        emailErrors.setVisibility(View.GONE);
        passwordErrors.setVisibility(View.GONE);
        confPasswordErrors.setVisibility(View.GONE);


        editTextEmail = (EditText) findViewById(R.id.inputEmail);
        editTextPassword = (EditText) findViewById(R.id.inputPassword);
        editTextConfirmPassword = (EditText) findViewById(R.id.confirmPassword);

        rg = (RadioGroup) findViewById(R.id.radioGroup);

        buttonRegister = (Button) findViewById(R.id.btnRegister);
        goToLogin = (TextView) findViewById(R.id.gotoLogin);
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
        Log.i("hey", "hey");
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
            Toast.makeText( getBaseContext(), "Please select a user type!",Toast.LENGTH_LONG).show();
            return;
        }

        if (!databaseHelper.checkUser(editTextEmail.getText().toString().trim())) {
            user.setEmail(editTextEmail.getText().toString().trim());
            user.setPassword(editTextPassword.getText().toString().trim());
            user.setRole(((RadioButton)findViewById(rg.getCheckedRadioButtonId())).getText().toString());
            databaseHelper.addUser(user);
            // Snack Bar to show success message that record saved successfully
//            Snackbar.make(nestedScrollView, getString(R.string.success_message), Snackbar.LENGTH_LONG).show();
            Toast.makeText( getBaseContext(), "User registered successfully",Toast.LENGTH_LONG).show();
            emptyInputEditText();
        } else {
            // Snack Bar to show error message that record already exists
//            Snackbar.make(nestedScrollView, getString(R.string.error_email_exists), Snackbar.LENGTH_LONG).show();
            Toast.makeText( getBaseContext(), "User registration error!",Toast.LENGTH_LONG).show();
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


