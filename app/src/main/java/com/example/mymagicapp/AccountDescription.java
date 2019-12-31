package com.example.mymagicapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.Context;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;
import android.content.ClipboardManager;
import android.widget.Toast;
import android.widget.ToggleButton;

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
        TextView txtUsername = findViewById(R.id.txtViewUsername);
        TextView txtCreationDate = findViewById(R.id.txtViewDate);
        final TextView txtPassword = findViewById(R.id.txtViewPassword);
        ToggleButton tglBtnPassword = findViewById(R.id.btnTogglePassword);
        //TextView txtPasswordC = findViewById(R.id.txtViewPasswordC);
        ImageButton btnCopy = findViewById(R.id.btnCopy);

        Account account = database.accountDao().findAccount(accountId);
        User user = database.userDao().findUser(account.userId);

        String userPassword = security.Decrypt(user.password,user.password);
        final String accountPassword = security.Decrypt(account.password, userPassword);

        txtDescription.setText(account.description);
        txtType.setText(account.type);
        txtUsername.setText(account.username);
        txtPassword.setText(accountPassword);
        txtCreationDate.setText(account.creationDate);
        //txtPasswordC.setText(account.password);

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

        btnCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("myMagicPassword", accountPassword);
                clipboard.setPrimaryClip(clip);

                Toast.makeText(getApplicationContext(),R.string.txt_copied,Toast.LENGTH_SHORT).show();
            }
        });
    }
}
