package com.example.mymagicapp.domain;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Account {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "userId")
    public int userId;

    @ColumnInfo(name = "description")
    public String description;

    @ColumnInfo(name = "type")
    public String type;

    @ColumnInfo(name = "password")
    public String password;
}
