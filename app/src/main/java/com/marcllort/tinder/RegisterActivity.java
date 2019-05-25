package com.marcllort.tinder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.textfield.TextInputEditText;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.marcllort.tinder.API.RegisterCallBack;
import com.marcllort.tinder.API.RestAPIManager;

public class RegisterActivity extends Activity implements RegisterCallBack {

    private TextInputEditText textUser;
    private TextInputEditText textMail;
    private TextInputEditText textPassword;
    private TextInputEditText textRePassword;
    private Button btnRegister;
    private TextView txtLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        textUser = findViewById(R.id.user_input_edit);
        textMail = findViewById(R.id.email_input_edit);
        textPassword = findViewById(R.id.password_input_edit);
        textRePassword = findViewById(R.id.repassword_input_edit);

        btnRegister = findViewById(R.id.btn_signup);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RegisterActivity.this, "Click Register! Camp password: " + textPassword.getText().toString() + " Camp rePassword: " + textRePassword.getText().toString(), Toast.LENGTH_SHORT).show();
                // Si es registra correctament, anem a la pantalla de login
                if (validate()) {
                    String username = textUser.getText().toString();
                    String email = textMail.getText().toString();
                    String password = textPassword.getText().toString();
                    String repassword = textRePassword.getText().toString();

                    // Implemetan el register AQUI
                    RestAPIManager.getInstance().register(username, email, password, RegisterActivity.this);

                    Intent profileIntent = new Intent(getApplicationContext(), ProfileCreateActivity.class);
                    startActivity(profileIntent);
                }
            }

        });

        txtLogin = findViewById(R.id.text_login);
        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    public boolean validate() {
        boolean valid = true;

        String user = textUser.getText().toString();
        String email = textMail.getText().toString();
        String password = textPassword.getText().toString();
        String repassword = textRePassword.getText().toString();

        if (user.isEmpty()) {
            textUser.setError(getText(R.string.error_user));
            valid = false;
        }

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            textMail.setError(getText(R.string.error_mail));
            valid = false;
        } else {
            textPassword.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            textPassword.setError(getText(R.string.error_pass));
            valid = false;
        } else {
            textPassword.setError(null);
        }

        if (repassword.isEmpty() || !repassword.equals(password)) {
            textRePassword.setError(getText(R.string.error_repass));
            valid = false;
        }

        return valid;
    }


    @Override
    public void onRegisterSuccess() {

    }

    @Override
    public void onFailure(Throwable t) {

    }
}