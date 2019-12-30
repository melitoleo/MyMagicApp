package com.example.mymagicapp.domain;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Type {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "description")
    public String description;

    public int accountCount;

    public String getDescription() {
        return description;
    }

    public int getAccountCount() {
        return accountCount;
    }
}
