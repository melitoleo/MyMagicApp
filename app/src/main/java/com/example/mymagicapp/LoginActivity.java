package com.example.mymagicapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mymagicapp.dao.AccountDatabase;
import com.example.mymagicapp.domain.User;

public class LoginActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final AccountDatabase database = AccountDatabase.getDatabase(getApplicationContext());

        boolean existUserIsEmpty = database.userDao().getAll().isEmpty();

        if(existUserIsEmpty) {
            Intent intent = new Intent(getApplicationContext(), RegistrationActivity.class);
            startActivity(intent);
            finish();
        }else{
            LoginUser(database);
        }
    }

    private void LoginUser(final AccountDatabase database) {

        final EditText username = findViewById(R.id.txtLoginUsername);
        final EditText txtPassword = findViewById(R.id.txtLoginPassword);
        final TextView txtNotFound = findViewById(R.id.txtNotFound);

        final Security security = new Security(getApplicationContext());

        Button btnLogin = findViewById(R.id.btnLogin);

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
