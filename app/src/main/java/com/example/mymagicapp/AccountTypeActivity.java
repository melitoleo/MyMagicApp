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
import com.example.mymagicapp.domain.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class AccountTypeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_type);

        final AccountDatabase database = AccountDatabase.getDatabase(getApplicationContext());
        TextView txtWelcome = findViewById(R.id.txtWelcome);
        FloatingActionButton fabAddType = findViewById(R.id.fabAddType);
        TableLayout tblDescription = findViewById(R.id.tblTypeDesc);

        final User user = database.userDao().getAll().get(0);

        txtWelcome.setText(String.format("Welcome %s", user.username));

        fabAddType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), TypeActivity.class);
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

            btnDescription.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(),AccountActivity.class);
                    intent.putExtra("userId", user.id);
                    intent.putExtra("type", description);
                    startActivity(intent);
                }
            });

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
}
