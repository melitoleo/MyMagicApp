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

        final EditText username = findViewById(R.id.txtLoginUsername);
        final EditText password = findViewById(R.id.txtLoginPassword);
        final TextView txtNotFound = findViewById(R.id.txtNotFound);

        Button btnLogin = findViewById(R.id.btnLogin);
        Button btnRegister = findViewById(R.id.btnOpenRegister);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = database.userDao().loginUser(username.getText().toString(), password.getText().toString());

                if(user==null){
                    txtNotFound.setText(String.format("User %s not found!", username.getText().toString()));
                }else{
                    Intent intent = new Intent(view.getContext(), AccountActivity.class);
                    intent.putExtra("userId", user.id);
                    startActivity(intent);
                }
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(view.getContext(), RegistrationActivity.class);
                startActivity(intent);
            }
        });
    }
}
