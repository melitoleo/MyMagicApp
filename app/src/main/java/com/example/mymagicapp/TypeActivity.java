package com.example.mymagicapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.mymagicapp.dao.AccountDatabase;
import com.example.mymagicapp.domain.Type;

public class TypeActivity extends AppCompatActivity {

    private RecyclerView viewTypeList;
    private DescriptionAdapter adapter;
    private ImageButton btnAddType;
    private EditText txtAddType;
    private AccountDatabase database;
    private String description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type);

        database = AccountDatabase.getDatabase(getApplicationContext());

        viewTypeList = findViewById(R.id.viewTypeList);
        btnAddType = findViewById(R.id.btnAddType);
        txtAddType = findViewById(R.id.txtAddType);

        adapter = new DescriptionAdapter(getApplicationContext());

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        viewTypeList.setLayoutManager(mLayoutManager);
        viewTypeList.setItemAnimator(new DefaultItemAnimator());
        viewTypeList.setAdapter(adapter);

        btnAddType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                description = txtAddType.getText().toString();

                if(database.typeDao().typeExist(description))
                    Toast.makeText(getApplicationContext(), "Tipo già esistente", Toast.LENGTH_LONG).show();
                else {
                    Type type = new Type();
                    type.description=description;
                    database.typeDao().insert(type);

                    Intent intent = new Intent(view.getContext(), MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}
