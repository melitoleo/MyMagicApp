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

    @Query("SELECT description FROM type ORDER BY description ASC")
    List<String> getAllDescription();

    @Query("SELECT t.id,  t.description AS description, t.hexcolor AS hexcolor, Count(a.type) AS accountCount FROM type t LEFT JOIN account a ON t.description=a.type GROUP BY t.description ORDER BY t.description ASC")
    List<Type> getTypeAggregate();

    @Query("DELETE FROM type WHERE description=:description")
    void deleteByDescription(String description);

    @Query("SELECT CAST(COUNT(1) AS BIT) FROM Type t WHERE t.description=:description")
    Boolean typeExist(String description);

    @Insert
    void insert(Type type);

    @Update
    void update(Type type);

    @Delete
    void delete(Type Type);
}
