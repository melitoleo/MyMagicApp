package com.example.mymagicapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.mymagicapp.dao.AccountDatabase;
import com.example.mymagicapp.domain.Account;
import com.example.mymagicapp.domain.User;
import com.google.android.material.textfield.TextInputEditText;

public class AddAccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_account);

        Bundle b = getIntent().getExtras();
        final int userId = b.getInt("userId");

        final Security security = new Security(getApplicationContext());
        final AccountDatabase database = AccountDatabase.getDatabase(getApplicationContext());


        final Spinner spnAccountType = findViewById(R.id.spnAccount);
        Button btnAddAccount = findViewById(R.id.btnAddAccount);
        final TextInputEditText txtDescription = findViewById(R.id.txtAccountDescription);
        final TextInputEditText txtPassword = findViewById(R.id.txtAccountPassword);

        final User user = database.userDao().findUser(userId);

        final String password = security.Decrypt(user.password, user.password);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.account_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnAccountType.setAdapter(adapter);

        btnAddAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Account account = new Account();
                account.userId = user.id;
                account.description = txtDescription.getText().toString();
                account.type = spnAccountType.getSelectedItem().toString();
                account.password = security.Encrypt(txtPassword.getText().toString(),password);

                database.accountDao().insert(account);

                Intent intent = new Intent(view.getContext(), AccountActivity.class);
                intent.putExtra("userId", user.id);
                startActivity(intent);
            }
        });
    }
}
