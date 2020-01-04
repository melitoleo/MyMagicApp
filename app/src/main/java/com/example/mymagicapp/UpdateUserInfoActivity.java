package com.example.mymagicapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.mymagicapp.dao.AccountDatabase;
import com.example.mymagicapp.domain.Type;
import com.example.mymagicapp.domain.User;

public class UpdateUserInfoActivity extends AppCompatActivity {

    private EditText name;
    private EditText surname;
    private EditText email;
    private EditText username;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user_info);

        Toolbar toolbar = findViewById(R.id.tlb_main);
        ToolBarManager.Setting(getApplicationContext(),toolbar, getString(R.string.user_info_title), null, MainActivity.class);

        final Button btnUpdate = findViewById(R.id.btnUpdateUserInfo);
        final Security security = new Security(getApplicationContext());

        name = findViewById(R.id.txtUpdateName);
        surname = findViewById(R.id.txtUpdateSurname);
        email = findViewById(R.id.txtUpdateMail);
        username = findViewById(R.id.txtUpdateUsername);
        password = findViewById(R.id.txtUpdatePassword);

        final AccountDatabase database = AccountDatabase.getDatabase(getApplicationContext());

        final User userInfo = database.userDao().getAll().get(0);

        name.setText(userInfo.name);
        surname.setText(userInfo.surname);
        email.setText(userInfo.email);
        username.setText(userInfo.username);
        password.setText(userInfo.password);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                User user = new User();

                user.id = userInfo.id;
                user.name = name.getText().toString();
                user.surname = surname.getText().toString();
                user.email = email.getText().toString();
                user.username = username.getText().toString();
                user.password = userInfo.password;
                //user.password = security.Encrypt(password.getText().toString(),password.getText().toString());

                database.userDao().update(user);

                Intent intent = new Intent(view.getContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
