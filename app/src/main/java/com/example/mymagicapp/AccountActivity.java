package com.example.mymagicapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.mymagicapp.dao.AccountDatabase;
import com.example.mymagicapp.domain.Account;
import com.example.mymagicapp.domain.Type;
import com.example.mymagicapp.domain.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class AccountActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        Bundle b = getIntent().getExtras();
        final int userId = b.getInt("userId");

        final AccountDatabase database = AccountDatabase.getDatabase(getApplicationContext());
        TextView txtWelcome = findViewById(R.id.txtWelcome);
        FloatingActionButton fabAddAccount = findViewById(R.id.fabAddAccount);
        TableLayout tblDescription = findViewById(R.id.tblDescription);
        //LinearLayout llAccount = findViewById(R.id.llAccount);

        User user = database.userDao().findUser(userId);

        txtWelcome.setText(String.format("Welcome %s", user.username));

        fabAddAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), AddAccountActivity.class);
                intent.putExtra("userId",userId);
                startActivity(intent);
            }
        });

        List<String> descriptions = database.typeDao().getAllDescription();

        TableRow row = CreateAndAddRow(tblDescription);

        for(final String description : descriptions){

            int index = descriptions.indexOf(description);
            int column = index%2;

            Button btnDescription = new Button(this);
            btnDescription.setId(index);
            btnDescription.setText(description);
            btnDescription.setLayoutParams(new TableRow.LayoutParams(column));

            row.addView(btnDescription);

            if(column==1)
                row = CreateAndAddRow(tblDescription);
        }
    }

    private TableRow CreateAndAddRow(TableLayout table){
        TableRow row = new TableRow(this);

        row.setLayoutParams(new TableLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));

        table.addView(row);

        return row;
    }

    private void BtnAccount(int userId, AccountDatabase database, LinearLayout llAccount) {
        List<Account> userAccount = database.accountDao().findUserAccount(userId);

        for(final Account item : userAccount){
            Button btnAccount = new Button(this);
            btnAccount.setText(item.description);
            btnAccount.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));

            btnAccount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), AccountDescription.class);
                    intent.putExtra("accountId", item.id);
                    startActivity(intent);
                }
            });

            llAccount.addView(btnAccount);
        }
    }
}
