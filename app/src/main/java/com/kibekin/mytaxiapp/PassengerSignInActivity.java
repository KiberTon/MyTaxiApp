package com.kibekin.mytaxiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class PassengerSignInActivity extends AppCompatActivity {

    private TextInputLayout textInputEmail;
    private TextInputLayout textInputName;
    private TextInputLayout textInputPassword;
    private TextInputLayout textInputConfirmPassword;

    private Button loginSingUpButton;
    private TextView toggleLoginSingUpTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_sign_in);

        textInputEmail = findViewById(R.id.textInputEmail);
        textInputName = findViewById(R.id.textInputName);
        textInputPassword = findViewById(R.id.textInputPassword);
        textInputConfirmPassword = findViewById(R.id.textInputConfirmPassword);

        loginSingUpButton = findViewById(R.id.loginSignUpButton);
        toggleLoginSingUpTextView = findViewById(R.id.toggleLoginSignUpTextView);
    }

    private boolean validateEmail() {

        String inputEmail = textInputEmail.getEditText().getText().toString().trim();

        if (inputEmail.isEmpty()) {
            textInputEmail.setError("Please input your email");
            return false;
        } else {
            textInputEmail.setError("");
            return true;
        }
    }

    private boolean validateName() {
        String inputName = textInputName.getEditText().getText().toString().trim();
        if (inputName.isEmpty()) {
            textInputName.setError("Please input your Name");
            return false;
        } else if (inputName.length() > 15) {
            textInputName.setError("Name length have to be less than 15");
            return false;
        } else {
            textInputName.setError("");
            return true;
        }
    }

    private boolean validatePassword() {
        String inputPassword = textInputPassword.getEditText().getText().toString().trim();
        String inputConfirmPassword = textInputConfirmPassword.getEditText().getText().toString().trim();
        if (inputPassword.isEmpty()) {
            textInputPassword.setError("Please input your password");
            return false;
        } else if (inputPassword.length() < 7) {
            textInputPassword.setError("Password length have to be more 6 ");
            return false;
        } else if (!inputPassword.equals(inputConfirmPassword)) {
            textInputConfirmPassword.setError("Password have to match");
            return false;
        } else {
            textInputPassword.setError("");
            textInputConfirmPassword.setError("");
            return true;
        }
    }

    public void loginSignUpPassenger(View view) {
        if (!validateEmail() | !validateName() | !validatePassword()) {
            return;
        }
        String inputPassenger = "Email: " + textInputEmail.getEditText().getText().toString().trim() + "\n" + "Name: " + textInputName.getEditText().getText().toString().trim()
                + "\n" + "Password: " + textInputPassword.getEditText().getText().toString().trim();

        Toast.makeText(this,inputPassenger, Toast.LENGTH_LONG).show();
    }

    public void toggleLoginSignUpPassenger(View view) {
    }
}
