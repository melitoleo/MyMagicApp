package com.example.mymagicapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
        final TextView txtPassword = findViewById(R.id.txtViewPassword);
        CheckBox cbkPasswordToggle = findViewById(R.id.ckbPasswordToggle);
        TextView txtPasswordC = findViewById(R.id.txtViewPasswordC);

        Account account = database.accountDao().findAccount(accountId);
        User user = database.userDao().findUser(account.userId);

        String userPassword = security.Decrypt(user.password,user.password);

        txtDescription.setText(account.description);
        txtType.setText(account.type);
        txtPassword.setText(security.Decrypt(account.password, userPassword));
        txtPasswordC.setText(account.password);

        cbkPasswordToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    txtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    txtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
    }
}
