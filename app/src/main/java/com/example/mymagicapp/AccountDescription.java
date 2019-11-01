package com.example.mymagicapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.mymagicapp.dao.AccountDatabase;
import com.example.mymagicapp.domain.Account;
import com.example.mymagicapp.domain.User;

public class AccountDescription extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_description);

        final Security security = new Security(getApplicationContext());

        Bundle b = getIntent().getExtras();
        final int accountId = b.getInt("accountId");
        final AccountDatabase database = AccountDatabase.getDatabase(getApplicationContext());

        TextView txtDescription = findViewById(R.id.txtViewDescription);
        TextView txtType = findViewById(R.id.txtViewType);
        TextView txtPassword = findViewById(R.id.txtViewPassword);
        TextView txtPasswordC = findViewById(R.id.txtViewPasswordC);

        Account account = database.accountDao().findAccount(accountId);
        User user = database.userDao().findUser(account.userId);

        txtDescription.setText(account.description);
        txtType.setText(account.type);
        txtPassword.setText(security.Decrypt(account.password, user.password));
        txtPasswordC.setText(account.password);
    }
}
