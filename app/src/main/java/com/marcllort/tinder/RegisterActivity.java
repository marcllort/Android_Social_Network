package com.marcllort.tinder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends Activity {

    private TextInputEditText textMail;
    private TextInputEditText textPassword;
    private TextInputEditText textRePassword;
    private Button btnRegister;
    private TextView txtLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        textMail = findViewById(R.id.email_input_edit);
        textPassword = findViewById(R.id.password_input_edit);
        textRePassword = findViewById(R.id.repassword_input_edit);

        btnRegister = findViewById(R.id.btn_signup);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RegisterActivity.this, "Click Register! Camp password: " + textPassword.getText().toString() + " Camp rePassword: " + textRePassword.getText().toString(), Toast.LENGTH_SHORT).show();
                // Si es registra correctament, anem a la pantalla de login
                finish();

                Intent profileIntent = new Intent(getApplicationContext(), ProfileCreateActivity.class);
                startActivity(profileIntent);
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


}
