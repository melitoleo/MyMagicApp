package com.example.mymagicapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.mymagicapp.dao.AccountDatabase;
import com.example.mymagicapp.domain.Type;
import com.example.mymagicapp.domain.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class RegistrationActivity extends AppCompatActivity {

    private EditText name;
    private EditText surname;
    private EditText email;
    private EditText username;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        final Button btnRegistration = findViewById(R.id.btnRegistration);
        final Security security = new Security(getApplicationContext());

        final AccountDatabase database = AccountDatabase.getDatabase(getApplicationContext());

        final String[] defaultTypeDescription = {"Social","Email"};

        btnRegistration.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                User user = new User();

                name = findViewById(R.id.txtName);
                surname = findViewById(R.id.txtSurname);
                email = findViewById(R.id.txtMail);
                username = findViewById(R.id.txtUsername);
                password = findViewById(R.id.txtPassword);

                user.name = name.getText().toString();
                user.surname = surname.getText().toString();
                user.email = email.getText().toString();
                user.username = username.getText().toString();
                user.password = security.Encrypt(password.getText().toString(),password.getText().toString());

                database.userDao().insert(user);

                for (String description: defaultTypeDescription) {
                    Type type = new Type();
                    type.description = description;
                    database.typeDao().insert(type);
                }

                Intent intent = new Intent(view.getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
