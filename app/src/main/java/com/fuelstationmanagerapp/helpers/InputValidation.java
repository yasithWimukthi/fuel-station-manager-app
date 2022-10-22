package com.fuelstationmanagerapp.helpers;
import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
//import android.support.design.widget.TextInputEditText;
//import android.support.design.widget.TextInputLayout;



public class InputValidation {
    private Context context;
    /**
     * constructor
     *
     * @param context
     */
    public InputValidation(Context context) {
        this.context = context;
    }

    /**
     * method to check InputEditText filled .
     *
     * @param editText
     * @param message
     * @param textView
     * @return
     */
    public boolean isInputEditTextFilled(EditText editText, TextView textView, String message) {
        String value = editText.getText().toString().trim();
        if (value.isEmpty()) {
            textView.setText(message);
            textView.setVisibility(View.VISIBLE);
            return false;
        } else {
            textView.setVisibility(View.GONE);
        }
        return true;
    }
}
