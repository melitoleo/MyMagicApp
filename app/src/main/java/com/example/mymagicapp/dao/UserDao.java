package com.example.mymagicapp.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.example.mymagicapp.domain.User;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * FROM user")
    List<User> getAll();

    @Query("SELECT * FROM user WHERE id LIKE :id LIMIT 1")
    User findUser(int id);

    @Query("SELECT * FROM user WHERE username = :username AND password = :password")
    User loginUser(String username, String password);

    @Insert
    void insert(User user);

    @Insert
    void insertAll(List<User> user);

    @Update
    void update(User user);

    @Delete
    void delete(User user);
}
