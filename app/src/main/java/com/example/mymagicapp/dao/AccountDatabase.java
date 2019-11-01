package com.example.mymagicapp.dao;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.mymagicapp.domain.Account;
import com.example.mymagicapp.domain.User;

@Database(entities = {User.class, Account.class}, version = 1, exportSchema = false)
public abstract class AccountDatabase extends RoomDatabase  {
    public abstract UserDao userDao();
    public abstract AccountDao accountDao();
    private static volatile AccountDatabase INSTANCE;

    public static AccountDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AccountDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AccountDatabase.class, "AccountDataBase").allowMainThreadQueries().build();
                }
            }
        }
        return INSTANCE;
    }
}
