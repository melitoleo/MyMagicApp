package com.example.mymagicapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mymagicapp.dao.AccountDatabase;
import com.example.mymagicapp.domain.Account;
import com.example.mymagicapp.domain.Type;
import com.example.mymagicapp.domain.User;

import java.util.ArrayList;
import java.util.List;

public class UpdateUserInfoActivity extends AppCompatActivity {

    private EditText name;
    private EditText surname;
    private EditText email;
    private EditText username;
    private EditText password;

    private AccountDatabase database;
    private User userInfo;

    private Security security;
    private String newSalt;
    private String userPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user_info);

        Toolbar toolbar = findViewById(R.id.tlb_main);
        ToolBarManager.Setting(getApplicationContext(),toolbar, getString(R.string.user_info_title), null, MainActivity.class);

        final Button btnUpdate = findViewById(R.id.btnUpdateUserInfo);
        security = new Security(getApplicationContext());

        name = findViewById(R.id.txtUpdateName);
        surname = findViewById(R.id.txtUpdateSurname);
        email = findViewById(R.id.txtUpdateMail);
        username = findViewById(R.id.txtUpdateUsername);
        password = findViewById(R.id.txtUpdatePassword);

        database = AccountDatabase.getDatabase(getApplicationContext());

        userInfo = database.userDao().getAll().get(0);
        userPassword = Session.getKey(getApplicationContext(),getString(R.string.password_key));

        name.setText(userInfo.name);
        surname.setText(userInfo.surname);
        email.setText(userInfo.email);
        username.setText(userInfo.username);
        password.setText(userPassword);

        Utility.setApplicationButton(this, btnUpdate);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                List<EditText> fieldsCheck = new ArrayList<>();

                fieldsCheck.add(username);
                fieldsCheck.add(name);
                fieldsCheck.add(password);

                if(!FieldManager.CheckFieldRequired(getApplicationContext(), fieldsCheck))

                updateUser(view);
            }
        });
    }

    private void updateUser(View view) {
        String newPassword = password.getText().toString();

        User user = new User();

        user.id = userInfo.id;
        user.name = name.getText().toString();
        user.surname = surname.getText().toString();
        user.email = email.getText().toString();
        user.username = username.getText().toString();

        if(newPassword!=userPassword) {
            if(updateAccountPasswords(newPassword)){
                user.salt = newSalt;
                user.password = security.Encrypt(newPassword,newPassword,newSalt);
                Session.addKey(getApplicationContext(), getString(R.string.password_key), newPassword);
            }else{
                Toast.makeText(this, "Aggiornamento Password non riuscito, si prega di riprovare", Toast.LENGTH_LONG).show();
            }
        }else {
            user.salt = userInfo.salt;
            user.password = userInfo.password;
        }

        database.userDao().update(user);

        Intent intent = new Intent(view.getContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }

    private boolean updateAccountPasswords(String password){
        try {
            List<Account> userAccounts = database.accountDao().findUserAccount(userInfo.id);

            newSalt = security.generateSalt(password);

            for (Account account : userAccounts) {
                String accountPass = security.Decrypt(account.password, userPassword, userInfo.salt);
                String encryptPass = security.Encrypt(accountPass, password, newSalt);
                database.accountDao().updatePassword(encryptPass, account.id);
            }
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
