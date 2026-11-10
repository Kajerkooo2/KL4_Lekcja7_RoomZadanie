package com.example.mblkcj6;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface GwintDAO {

    @Insert
    void insert(Gwint card);

    @Delete
    void delete(Gwint card);
    @Query("SELECT * FROM gwint_cards ORDER BY id DESC")
    List<Gwint> getAllCards();
}
