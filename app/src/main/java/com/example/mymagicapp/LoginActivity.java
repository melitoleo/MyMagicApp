package com.example.mymagicapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mymagicapp.dao.AccountDatabase;
import com.example.mymagicapp.domain.User;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText username;
    private TextInputEditText txtPassword;
    private TextView txtNotFound;
    private Button btnLogin;

    private AccountDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final Security security = new Security(getApplicationContext());

        database = AccountDatabase.getDatabase(getApplicationContext());

        username = findViewById(R.id.txtLoginUsername);
        txtPassword = findViewById(R.id.txtLoginPassword);
        txtNotFound = findViewById(R.id.txtNotFound);
        btnLogin = findViewById(R.id.btnLogin);

        Utility.setApplicationButton(this, btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String salt = database.userDao().getSalt();
                String dgtPassword = txtPassword.getText().toString();

                String password = security.Encrypt(dgtPassword, dgtPassword, salt);

                User user = database.userDao().loginUser(username.getText().toString(), password);

                if (user == null) {
                    txtNotFound.setText(getString(R.string.prompt_error));
                } else {
                    Session.addKey(getApplicationContext(),getString(R.string.password_key), dgtPassword);

                    Intent intent = new Intent(view.getContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}
