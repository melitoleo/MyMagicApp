package com.example.mymagicapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.mymagicapp.dao.AccountDatabase;
import com.example.mymagicapp.domain.Account;
import com.example.mymagicapp.domain.User;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class UpdateAccountActivity extends AppCompatActivity {

    private TextInputEditText txtDescription;
    private TextInputEditText txtType;
    private TextInputEditText txtUsername;
    private TextView txtCreationDate;
    private TextInputEditText txtPassword;
    private TextInputEditText txtNewPassword;
    private TextInputEditText txtConfirmNewPassword;
    private ToggleButton tglBtnPassword;
    private Button btnUpdate;
    private TextView txtUpdateStrPassword;
    private TextView txtUpdateCheckPassword;

    private AccountDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_account);

        final Security security = new Security(getApplicationContext());

        Bundle b = getIntent().getExtras();
        final int accountId = b.getInt("accountId");
        database = AccountDatabase.getDatabase(getApplicationContext());

        final Account account = database.accountDao().findAccount(accountId);
        final User user = database.userDao().findUser(account.userId);

        Toolbar toolbar = findViewById(R.id.tlb_main);
        ToolBarManager.Setting(getApplicationContext(),toolbar, getString(R.string.update_account_title), account.type, AccountActivity.class);

        txtDescription = findViewById(R.id.txtUpdateDescription);
        txtType = findViewById(R.id.txtUpdateType);
        txtUsername = findViewById(R.id.txtUpdateUsername);
        txtCreationDate = findViewById(R.id.txtUpdateDate);
        txtPassword = findViewById(R.id.txtUpdatePassword);
        txtNewPassword = findViewById(R.id.txtUpdateNewPassword);
        txtConfirmNewPassword = findViewById(R.id.txtUpdateRepeatPassword);
        tglBtnPassword = findViewById(R.id.btnUpdateTogglePassword);
        btnUpdate = findViewById(R.id.btnUpdateAccount);
        txtUpdateStrPassword = findViewById(R.id.txtUpdateStrPassword);
        txtUpdateCheckPassword = findViewById(R.id.txtUpdateCheckPassword);

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

        passwordStrength();

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<EditText> fieldsCheck = new ArrayList<>();

                fieldsCheck.add(txtDescription);
                fieldsCheck.add(txtUsername);
                fieldsCheck.add(txtPassword);

                if(!FieldManager.CheckFieldRequired(getApplicationContext(), fieldsCheck))
                    updateAccount(accountId, account);
            }
        });
    }

    private void passwordStrength() {
        txtNewPassword.addTextChangedListener(new TextWatcher() {
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
    }

    private void checkPassword(){
        txtConfirmNewPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String pwd = txtNewPassword.getText().toString();
                String ckPwd = txtConfirmNewPassword.getText().toString();

                txtUpdateCheckPassword.setText("");
                btnUpdate.setEnabled(false);

                if(s.length() >= pwd.length()) {
                    if (!PasswordStrength.PasswordCheck(pwd, ckPwd))
                        txtUpdateCheckPassword.setText(getString(R.string.password_ko_check_text));
                    else {
                        txtUpdateCheckPassword.setText(getString(R.string.password_ok_check_text));
                        btnUpdate.setEnabled(true);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void updateAccount(int accountId, Account account) {
        Account updateAccount = new Account();

        updateAccount.id = accountId;
        updateAccount.description = txtDescription.getText().toString();
        updateAccount.type = txtType.getText().toString();
        updateAccount.username = txtUsername.getText().toString();
        updateAccount.password = account.password;
        updateAccount.creationDate = txtCreationDate.getText().toString();
        updateAccount.userId = account.userId;

        database.accountDao().update(updateAccount);

        Intent intent = new Intent(getApplicationContext(), AccountDescription.class);
        intent.putExtra("accountId", accountId);
        startActivity(intent);
        finish();
    }

    private void calculatePasswordStrength(String str) {
        PasswordStrength passwordStrength = PasswordStrength.calculate(str);
        txtUpdateStrPassword.setText(passwordStrength.msg);
        txtUpdateStrPassword.setTextColor(passwordStrength.color);
    }
}
