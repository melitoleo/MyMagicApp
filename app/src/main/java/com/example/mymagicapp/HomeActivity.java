package com.example.mymagicapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.mymagicapp.dao.AccountDatabase;

public class HomeActivity extends AppCompatActivity {

    private AccountDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        database = AccountDatabase.getDatabase(getApplicationContext());

        boolean existUserIsEmpty = database.userDao().getAll().isEmpty();

        if(existUserIsEmpty) {
            Intent intent = new Intent(getApplicationContext(), RegistrationActivity.class);
            startActivity(intent);
            finish();
        }else{
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
