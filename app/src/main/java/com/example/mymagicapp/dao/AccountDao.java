package com.example.mymagicapp.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mymagicapp.domain.Account;
import com.example.mymagicapp.domain.User;

import java.util.List;

@Dao
public interface AccountDao {

    @Query("SELECT * FROM account WHERE userId = :userId")
    List<Account> findUserAccount(int userId);

    @Query("SELECT * FROM account WHERE id = :id")
    Account findAccount(int id);

    @Insert
    void insert(Account account);

    @Update
    void update(Account account);

    @Delete
    void delete(Account account);
}
