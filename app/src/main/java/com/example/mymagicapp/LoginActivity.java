package com.example.mymagicapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String password = security.Encrypt(txtPassword.getText().toString(), txtPassword.getText().toString());

                User user = database.userDao().loginUser(username.getText().toString(), password);

                if (user == null) {
                    txtNotFound.setText(String.format("User %s not found!", username.getText().toString()));
                } else {
                    Intent intent = new Intent(view.getContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}
