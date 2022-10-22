package com.fuelstationmanagerapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.widget.NestedScrollView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.fuelstationmanagerapp.helpers.InputValidation;
import com.fuelstationmanagerapp.model.User;
import com.fuelstationmanagerapp.sql.DatabaseHelper;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterScreen extends AppCompatActivity {

    private final AppCompatActivity activity = RegisterScreen.this;

    private NestedScrollView nestedScrollView;

    private TextInputLayout textInputLayoutEmail;
    private TextInputLayout textInputLayoutPassword;
    private TextInputLayout textInputLayoutConfirmPassword;

    private EditText textInputEditTextEmail;
    private EditText textInputEditTextPassword;
    private EditText textInputEditTextConfirmPassword;

    private Button buttonRegister;
    private AppCompatTextView appCompatTextViewLoginLink;
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
//        nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);

        textInputEditTextEmail = (EditText) findViewById(R.id.inputEmail);
        textInputEditTextPassword = (EditText) findViewById(R.id.inputPassword);
        textInputEditTextConfirmPassword = (EditText) findViewById(R.id.confirmPassword);

        buttonRegister = (Button) findViewById(R.id.btnRegister);
//        appCompatTextViewLoginLink = (AppCompatTextView) findViewById(R.id.appCompatTextViewLoginLink);
    }
}