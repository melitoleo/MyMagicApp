package com.example.mymagicapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

    private TextInputEditText txtDescription;
    private TextInputEditText txtPassword;
    private TextInputEditText txtUsername;
    private TextView txtAddPwStrength;

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
        
        txtDescription = findViewById(R.id.txtAccountDescription);
        txtPassword = findViewById(R.id.txtAccountPassword);
        txtUsername = findViewById(R.id.txtAccountUsername);
        txtAddPwStrength = findViewById(R.id.txtAddPwStrength);

        final User user = database.userDao().getAll().get(0);

        final String password = security.Decrypt(user.password, user.password);

        txtPassword.addTextChangedListener(new TextWatcher() {
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
                finish();
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

    private void calculatePasswordStrength(String str) {
        // Now, we need to define a PasswordStrength enum
        // with a calculate static method returning the password strength
        PasswordStrength passwordStrength = PasswordStrength.calculate(str);
        txtAddPwStrength.setText(passwordStrength.msg);
        txtAddPwStrength.setTextColor(passwordStrength.color);
        //root.setBackgroundColor(passwordStrength.color);
    }
}
