package org.erusakov.diplomaclient;

import android.content.Context;

import com.google.android.material.textfield.TextInputLayout;

public class Validation {
    public static Boolean validateIsEmpty(TextInputLayout textInputLayout, Context context) {
        String val = textInputLayout.getEditText().getText().toString();

        if (val.isEmpty()) {
            textInputLayout.setError(context.getString(R.string.empty_error));
            return false;
        }
        else {
            textInputLayout.setError(null);
            textInputLayout.setErrorEnabled(false);
            return true;
        }
    }

    public static Boolean validateIsLonger8(TextInputLayout textInputLayout, Context context) {
        String val = textInputLayout.getEditText().getText().toString();

        if (val.length() <= 8) {
            textInputLayout.setError(context.getString(R.string.short8_error));
            return false;
        }
        else {
            textInputLayout.setError(null);
            textInputLayout.setErrorEnabled(false);
            return true;
        }
    }

    public static Boolean validateIsShorter15(TextInputLayout textInputLayout, Context context) {
        String val = textInputLayout.getEditText().getText().toString();

        if (val.length() >= 15) {
            textInputLayout.setError(context.getString(R.string.long15_error));
            return false;
        }
        else {
            textInputLayout.setError(null);
            textInputLayout.setErrorEnabled(false);
            return true;
        }
    }

    public static Boolean validateWhiteSpace(TextInputLayout textInputLayout, Context context) {
        String val = textInputLayout.getEditText().getText().toString();
        String whiteSpace = "(?=\\s+$)";

        if (!val.matches(whiteSpace)) {
            textInputLayout.setError(context.getString(R.string.white_space_error));
            return false;
        }
        else {
            textInputLayout.setError(null);
            textInputLayout.setErrorEnabled(false);
            return true;
        }
    }

    public static Boolean validateEmail(TextInputLayout textInputLayout, Context context) {
        String val = textInputLayout.getEditText().getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (!val.matches(emailPattern)) {
            textInputLayout.setError(context.getString(R.string.email_error));
            return false;
        }
        else {
            textInputLayout.setError(null);
            textInputLayout.setErrorEnabled(false);
            return true;
        }
    }

    public static Boolean validatePassword(TextInputLayout textInputLayout, Context context) {
        String val = textInputLayout.getEditText().getText().toString();
        String passwordRules = "^" +
                "(?=.*[0-9])" +         //at least 1 digit
                "(?=.*[a-z])" +         //at least 1 lower case letter
                "(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{4,}" +               //at least 4 characters
                "$";

        if (!val.matches(passwordRules)) {
            textInputLayout.setError(context.getString(R.string.password_weak_error));
            return false;
        }
        else {
            textInputLayout.setError(null);
            textInputLayout.setErrorEnabled(false);
            return true;
        }
    }

    public static Boolean validateEqualString(TextInputLayout textInputLayout, Context context, String string) {
        String val = textInputLayout.getEditText().getText().toString();

        if (!val.equals(string)) {
            textInputLayout.setError(context.getString(R.string.password_equals_error));
            return false;
        }
        else {
            textInputLayout.setError(null);
            textInputLayout.setErrorEnabled(false);
            return true;
        }
    }
}
