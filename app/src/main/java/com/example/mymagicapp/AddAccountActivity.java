package com.example.mymagicapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.mymagicapp.dao.AccountDatabase;
import com.example.mymagicapp.domain.Account;
import com.example.mymagicapp.domain.User;
import com.google.android.material.textfield.TextInputEditText;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AddAccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_account);

        Bundle b = getIntent().getExtras();
        final String type = b.getString("type");

        Toolbar toolbar = findViewById(R.id.tlb_main);
        ToolBarManager.Setting(getApplicationContext(),toolbar, String.format(getString(R.string.account_new_title), type), type, AccountActivity.class);

        final Security security = new Security(getApplicationContext());
        final AccountDatabase database = AccountDatabase.getDatabase(getApplicationContext());

        Button btnAddAccount = findViewById(R.id.btnAddAccount);

        TextView txtType =  findViewById(R.id.txtAddType);
        txtType.setText(type);
        
        final TextInputEditText txtDescription = findViewById(R.id.txtAccountDescription);
        final TextInputEditText txtPassword = findViewById(R.id.txtAccountPassword);
        final TextInputEditText txtUsername = findViewById(R.id.txtAccountUsername);

        final User user = database.userDao().getAll().get(0);

        final String password = security.Decrypt(user.password, user.password);

        btnAddAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Account account = new Account();
                account.userId = user.id;
                account.description = txtDescription.getText().toString();
                account.type = type;
                account.username = txtUsername.getText().toString();
                account.password = security.Encrypt(txtPassword.getText().toString(),password);
                account.creationDate = GetDateFormat();

                database.accountDao().insert(account);

                Intent intent = new Intent(view.getContext(), AccountActivity.class);
                intent.putExtra("userId", user.id);
                intent.putExtra("type", type);
                startActivity(intent);
            }
        });
    }

    private String GetDateFormat(){
        Locale.setDefault(Locale.ITALIAN);
        SimpleDateFormat dataFormat = new SimpleDateFormat("dd-MM-yyyy");
        long millis=System.currentTimeMillis();
        final Date date=new Date(millis);
        return dataFormat.format(date);
    }
}
