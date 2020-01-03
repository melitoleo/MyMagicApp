package com.example.mymagicapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import com.example.mymagicapp.dao.AccountDatabase;
import com.example.mymagicapp.domain.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AccountDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = AccountDatabase.getDatabase(getApplicationContext());
        final User user = database.userDao().getAll().get(0);

        Toolbar toolbar = findViewById(R.id.tlb_main);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Home");
        getSupportActionBar().setSubtitle(String.format("Benvenuto %s", user.username));

        FloatingActionButton fabAddType = findViewById(R.id.fabAddType);
        TableLayout tblDescription = findViewById(R.id.tblTypeDesc);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.menuAbout:
                Toast.makeText(this, "You clicked about", Toast.LENGTH_SHORT).show();
                break;

            case R.id.menuSettings:
                Toast.makeText(this, "You clicked settings", Toast.LENGTH_SHORT).show();
                break;

            case R.id.menuLogout:
                Toast.makeText(this, "You clicked logout", Toast.LENGTH_SHORT).show();
                break;

        }
        return true;
    }
}
