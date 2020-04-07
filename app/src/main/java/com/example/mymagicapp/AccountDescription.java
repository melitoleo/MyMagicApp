package com.example.mymagicapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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

    private TextView txtType;
    private TextView txtUsername;
    private TextView txtCreationDate;
    private TextView txtPassword;
    private ToggleButton tglBtnPassword;
    private ImageButton btnCopy;
    private int accountId;
    private AccountDatabase database;
    private Account account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_description);

        final Security security = new Security(getApplicationContext());

        Bundle b = getIntent().getExtras();
        accountId = b.getInt("accountId");

        database = AccountDatabase.getDatabase(getApplicationContext());

        account = database.accountDao().findAccount(accountId);
        User user = database.userDao().findUser(account.userId);

        Toolbar toolbar = findViewById(R.id.tlb_main);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(String.format(getString(R.string.account_desc_title), account.description));
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AccountActivity.class);
                intent.putExtra("type", account.type);
                startActivity(intent);
                finish();
            }
        });

        txtType = findViewById(R.id.txtViewType);
        txtUsername = findViewById(R.id.txtViewUsername);
        txtCreationDate = findViewById(R.id.txtViewDate);
        txtPassword = findViewById(R.id.txtViewPassword);
        tglBtnPassword = findViewById(R.id.btnTogglePassword);
        btnCopy = findViewById(R.id.btnCopy);

        String userPassword = security.Decrypt(user.password,user.password);
        final String accountPassword = security.Decrypt(account.password, userPassword);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.account_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch(item.getItemId()){
            case R.id.account_update:
                intent = new Intent(getApplicationContext(), UpdateAccountActivity.class);
                intent.putExtra("accountId", accountId);
                startActivity(intent);
                finish();
                break;

            case R.id.account_delete:
                database.accountDao().delete(account);
                intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
                break;
        }
        return true;
    }
}
