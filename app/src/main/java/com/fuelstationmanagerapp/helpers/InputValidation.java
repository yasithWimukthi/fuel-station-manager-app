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
            hideKeyboardFrom(textView);
            return false;
        } else {
            textView.setVisibility(View.GONE);
        }
        return true;
    }

    /**
     * method to check InputEditText has valid email .
     *
     * @param editText
     * @param textView
     * @param message
     * @return
     */
    public boolean isInputEditTextEmail(EditText editText, TextView textView , String message) {
        String value = editText.getText().toString().trim();
        if (value.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(value).matches()) {
            textView.setText(message);
            textView.setVisibility(View.VISIBLE);
            hideKeyboardFrom(textView);
            return false;
        } else {
            textView.setVisibility(View.GONE);
        }
        return true;
    }

    public boolean isInputEditTextMatches(EditText editText1, EditText editText2, TextView textView, String message) {
        String value1 = editText1.getText().toString().trim();
        String value2 = editText2.getText().toString().trim();
        if (!value1.contentEquals(value2)) {
            textView.setText(message);
            textView.setVisibility(View.VISIBLE);
            hideKeyboardFrom(textView);
            return false;
        } else {
            textView.setVisibility(View.GONE);
        }
        return true;
    }
    /**
     * method to Hide keyboard
     *
     * @param view
     */
    private void hideKeyboardFrom(View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }
}
