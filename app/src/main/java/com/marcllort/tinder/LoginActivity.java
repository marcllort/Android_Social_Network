package com.marcllort.tinder;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.marcllort.tinder.API.LoginCallBack;
import com.marcllort.tinder.API.RegisterCallBack;
import com.marcllort.tinder.API.RestAPIManager;
import com.marcllort.tinder.API.UserToken;

public class LoginActivity extends Activity implements LoginCallBack {

    private TextInputEditText textMail;
    private TextInputEditText textPassword;
    private Button btnLogin;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        textMail = findViewById(R.id.email_input_edit);
        textPassword = findViewById(R.id.password_input_edit);


        btnLogin = findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "Click login! Camp login: " + textMail.getText().toString() + " Pass: " + textPassword.getText().toString(), Toast.LENGTH_SHORT).show();
                // Si login correcte, anem a main activity
                attemptLogin();
            }
        });


        btnRegister = findViewById(R.id.btn_signup);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "Click Register! Camp password: " + textPassword.getText().toString(), Toast.LENGTH_SHORT).show();

                Intent registerIntent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(registerIntent);
            }
        });

    }


    private void attemptLogin() {
        // Reset errors.
        textMail.setError(null);
        textPassword.setError(null);

        // Store values at the time of the login attempt.
        String username = textMail.getText().toString();
        String password = textPassword.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            textPassword.setError(getString(R.string.error_pass));
            focusView = textPassword;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(username)) {
            textMail.setError(getString(R.string.error_username));
            focusView = textMail;
            cancel = true;
        } else if (!isEmailValid(username)) {
            textMail.setError(getString(R.string.error_username));
            focusView = textMail;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            RestAPIManager.getInstance().getUserToken(username, password, this);

        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.length() > 2;
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 3;
    }

    @Override
    public void onLoginSuccess(UserToken userToken) {
        Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(mainIntent);
        finish();
    }

    @Override
    public void onFailure(Throwable t) {
        new AlertDialog.Builder(this)
                .setTitle("Login Error")
                .setMessage(t.getMessage())


                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with delete operation
                    }
                })

                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }


}
