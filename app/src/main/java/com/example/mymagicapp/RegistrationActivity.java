package com.example.mymagicapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mymagicapp.dao.AccountDatabase;
import com.example.mymagicapp.domain.Type;
import com.example.mymagicapp.domain.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class RegistrationActivity extends AppCompatActivity {

    private EditText name;
    private EditText surname;
    private EditText email;
    private EditText username;
    private EditText password;
    private EditText confirmPassword;
    private TextView txtPwStrength;
    private TextView txtPwCheck;
    private Button btnRegistration;

    private Security security;
    private AccountDatabase database;

    private String[] defaultTypeDescription = {"Social","Email","a","b","c","d","e"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        btnRegistration = findViewById(R.id.btnRegistration);

        security = new Security(getApplicationContext());
        database = AccountDatabase.getDatabase(getApplicationContext());

        name = findViewById(R.id.txtName);
        surname = findViewById(R.id.txtSurname);
        email = findViewById(R.id.txtMail);
        username = findViewById(R.id.txtUsername);
        password = findViewById(R.id.txtPassword);
        confirmPassword = findViewById(R.id.txtConfirmPassword);
        txtPwStrength = findViewById(R.id.txtPwStrength);
        txtPwCheck = findViewById(R.id.txtPwCheck);

        btnRegistration.setEnabled(false);

        btnRegistration.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                List<EditText> fieldsCheck = new ArrayList<>();

                fieldsCheck.add(name);
                fieldsCheck.add(username);

                if(!FieldManager.CheckFieldRequired(getApplicationContext(), fieldsCheck))
                    addUser(view);
            }
        });

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                calculatePasswordStrength(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }

        });

        confirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String pwd = password.getText().toString();
                String ckPwd = confirmPassword.getText().toString();

                txtPwCheck.setText("");
                btnRegistration.setEnabled(false);

                if(s.length() >= pwd.length()) {
                    if (!PasswordStrength.PasswordCheck(pwd, ckPwd))
                        txtPwCheck.setText(getString(R.string.password_ko_check_text));
                    else {
                        txtPwCheck.setText(getString(R.string.password_ok_check_text));
                        btnRegistration.setEnabled(true);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void addUser(View view) {
        User user = new User();

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
        finish();
    }

    private void calculatePasswordStrength(String str) {
        PasswordStrength passwordStrength = PasswordStrength.calculate(str);
        txtPwStrength.setText(passwordStrength.msg);
        txtPwStrength.setTextColor(passwordStrength.color);
    }
}
