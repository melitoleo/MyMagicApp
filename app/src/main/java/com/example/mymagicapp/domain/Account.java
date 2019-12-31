package com.example.mymagicapp.domain;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Date;

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

    @ColumnInfo(name = "username")
    public String username;

    @ColumnInfo(name = "password")
    public String password;

    @ColumnInfo(name = "creationDate")
    public String creationDate;
}
