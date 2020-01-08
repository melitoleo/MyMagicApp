package com.example.mymagicapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.mymagicapp.dao.AccountDatabase;
import com.example.mymagicapp.domain.Account;
import com.example.mymagicapp.domain.User;
import com.google.android.material.textfield.TextInputEditText;

public class UpdateAccountActivity extends AppCompatActivity {

    private TextInputEditText txtDescription;
    private TextInputEditText txtType;
    private TextView txtUsername;
    private TextView txtCreationDate;
    private TextView txtPassword;
    private ToggleButton tglBtnPassword;
    private Button btnUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_account);

        final Security security = new Security(getApplicationContext());

        Bundle b = getIntent().getExtras();
        final int accountId = b.getInt("accountId");
        final AccountDatabase database = AccountDatabase.getDatabase(getApplicationContext());

        final Account account = database.accountDao().findAccount(accountId);
        final User user = database.userDao().findUser(account.userId);

        Toolbar toolbar = findViewById(R.id.tlb_main);
        ToolBarManager.Setting(getApplicationContext(),toolbar, getString(R.string.account_desc_title), account.type, AccountActivity.class);

        txtDescription = findViewById(R.id.txtUpdateDescription);
        txtType = findViewById(R.id.txtUpdateType);
        txtUsername = findViewById(R.id.txtUpdateUsername);
        txtCreationDate = findViewById(R.id.txtUpdateDate);
        txtPassword = findViewById(R.id.txtUpdatePassword);
        tglBtnPassword = findViewById(R.id.btnUpdateTogglePassword);
        btnUpdate = findViewById(R.id.btnUpdateAccount);

        String userPassword = security.Decrypt(user.password,user.password);
        final String accountPassword = security.Decrypt(account.password, userPassword);

        txtDescription.setText(account.description);
        txtType.setText(account.type);
        txtUsername.setText(account.username);
        txtPassword.setText(accountPassword);
        txtCreationDate.setText(account.creationDate);

        txtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());

        tglBtnPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    txtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    txtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Account updateAccount = new Account();

                updateAccount.id = accountId;
                updateAccount.description = txtDescription.getText().toString();
                updateAccount.type = txtType.getText().toString();
                updateAccount.username = txtUsername.getText().toString();
                updateAccount.password = account.password;
                updateAccount.creationDate = txtCreationDate.getText().toString();
                updateAccount.userId = account.userId;

                database.accountDao().update(updateAccount);

                Intent intent = new Intent(getApplicationContext(),AccountDescription.class);
                intent.putExtra("accountId", accountId);
                startActivity(intent);
                finish();
            }
        });
    }
}
