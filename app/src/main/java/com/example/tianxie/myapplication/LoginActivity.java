package com.example.tianxie.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    private EditText mUsername;
    private EditText mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.setTitle("Login");
        mUsername = (EditText) findViewById(R.id.login_edit_test_username);
        mPassword = (EditText) findViewById(R.id.login_edit_test_password);
        AppCompatButton mLoginBtn = (AppCompatButton) findViewById(R.id.login_button_login);
        AppCompatButton mSignupBtn = (AppCompatButton) findViewById(R.id.login_button_register);

        mSignupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = mUsername.getText().toString();
                String password = mPassword.getText().toString();
                if ("hfaltgg".equals(username) && "12345678".equals(password)) {
                    Intent intent;
                    intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast toast = Toast.makeText(LoginActivity.this, "invalid username or password", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }

}
