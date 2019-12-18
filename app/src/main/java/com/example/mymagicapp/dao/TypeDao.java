package com.example.mymagicapp.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mymagicapp.domain.Type;

import java.util.List;

@Dao
public interface TypeDao {

    @Query("SELECT * FROM type")
    List<Type> findAll();

    @Query("SELECT description FROM type")
    List<String> getAllDescription();

    @Insert
    void insert(Type type);

    @Update
    void update(Type type);

    @Delete
    void delete(Type Type);
}
