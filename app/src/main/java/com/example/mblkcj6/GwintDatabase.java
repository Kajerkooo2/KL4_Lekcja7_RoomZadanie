package com.example.mblkcj6;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Gwint.class}, version = 1, exportSchema = false)
public abstract class GwintDatabase extends RoomDatabase {
    public abstract GwintDAO getAllCards();
}
