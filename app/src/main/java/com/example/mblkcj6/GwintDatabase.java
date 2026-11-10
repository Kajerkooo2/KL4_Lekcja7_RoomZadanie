package com.example.mblkcj6;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Gwint.class}, version = 1, exportSchema = false)
public abstract class GwintDatabase extends RoomDatabase {
    public abstract GwintDAO gwintDAO();

    public static GwintDatabase getInstance(Context context){
        return Room.databaseBuilder(context,GwintDatabase.class,"gwint_database")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
    }
}
