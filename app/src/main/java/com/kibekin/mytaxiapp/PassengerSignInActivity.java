package com.kibekin.mytaxiapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class PassengerSignInActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private static final String TAG = "SignUpPassenger";
    private TextInputLayout textInputEmail;
    private TextInputLayout textInputName;
    private TextInputLayout textInputPassword;
    private TextInputLayout textInputConfirmPassword;

    private Button loginSingUpButton;
    private TextView toggleLoginSingUpTextView;

    private boolean isLoginModeActive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_sign_in);

        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(PassengerSignInActivity.this, PassengerMapsActivity.class));
        }

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

        if (inputPassword.isEmpty()) {
            textInputPassword.setError("Please input your password");
            return false;
        } else if (inputPassword.length() < 7) {
            textInputPassword.setError("Password length have to be more 6 ");
            return false;
        } else {
            textInputPassword.setError("");
            textInputConfirmPassword.setError("");
            return true;
        }
    }

    private boolean validateConfirmPassword() {

        String passwordInput = textInputPassword.getEditText().getText().toString().trim();
        String confirmPasswordInput = textInputConfirmPassword.getEditText().getText().toString().trim();

        if (!passwordInput.equals(confirmPasswordInput)) {
            textInputPassword.setError("Password have to match");
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
        if (isLoginModeActive) {
            auth.signInWithEmailAndPassword(textInputEmail.getEditText().getText().toString().trim(), textInputPassword.getEditText().getText().toString().trim())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithEmail:success");
                                FirebaseUser user = auth.getCurrentUser();
                                startActivity(new Intent(PassengerSignInActivity.this, PassengerMapsActivity.class));
//                                updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(PassengerSignInActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
//                                updateUI(null);
                            }

                        }
                    });
        } else {

            if (!validateEmail() | !validateName() | !validatePassword() | !validateConfirmPassword()) {
                return;
            }

            auth.createUserWithEmailAndPassword(textInputEmail.getEditText().getText().toString().trim(), textInputPassword.getEditText().getText().toString().trim())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success");
                                FirebaseUser user = auth.getCurrentUser();
                                startActivity(new Intent(PassengerSignInActivity.this, PassengerMapsActivity.class));
//                            updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(PassengerSignInActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
//                            updateUI(null);
                            }
                        }
                    });
        }
    }


    public void toggleLoginSignUpPassenger(View view) {
        if (isLoginModeActive) {
            isLoginModeActive = false;
            loginSingUpButton.setText("Sign Up");
            toggleLoginSingUpTextView.setText("Or, log in");
            textInputConfirmPassword.setVisibility(View.VISIBLE);
        } else {
            isLoginModeActive = true;
            loginSingUpButton.setText("Log In");
            toggleLoginSingUpTextView.setText("Or, sign up");
            textInputConfirmPassword.setVisibility(View.GONE);
        }
    }
}
