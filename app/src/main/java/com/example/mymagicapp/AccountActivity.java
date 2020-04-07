package com.example.mymagicapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import com.example.mymagicapp.dao.AccountDatabase;
import com.example.mymagicapp.domain.Account;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class AccountActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);


        Bundle b = getIntent().getExtras();
        final String type = b.getString("type");

        Toolbar toolbar = findViewById(R.id.tlb_main);
        ToolBarManager.Setting(getApplicationContext(),toolbar, type, type, MainActivity.class);

        final AccountDatabase database = AccountDatabase.getDatabase(getApplicationContext());
        FloatingActionButton fabAddAccount = findViewById(R.id.fabAddAccount);
        LinearLayout llAccount = findViewById(R.id.llAccount);

        fabAddAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), AddAccountActivity.class);
                intent.putExtra("type",type);
                startActivity(intent);
                finish();
            }
        });

        List<Account> userAccount = database.accountDao().findUserAccountByType(type);

        String hexColor = database.typeDao().getHexColorByDescription(type);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                200
        );
        params.setMargins(8, 8, 8, 8);

        for(final Account item : userAccount){
            Button btnAccount = new Button(this);
            btnAccount.setText(item.description);
            btnAccount.setTextColor(Color.parseColor(getString(R.string.colorText_text)));
            btnAccount.setLayoutParams(params);
            btnAccount.setBackground(Utility.setRounded(hexColor));
            btnAccount.setElevation(15f);
            btnAccount.setTranslationZ(15f);
            btnAccount.setZ(30f);
            btnAccount.setTop(8);
            btnAccount.setBottom(8);

            btnAccount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(),AccountDescription.class);
                    intent.putExtra("accountId", item.id);
                    startActivity(intent);
                    finish();
                }
            });

            llAccount.addView(btnAccount);
        }
    }
}
