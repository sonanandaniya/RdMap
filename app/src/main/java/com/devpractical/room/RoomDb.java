package com.devpractical.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = {CityData.class}, version = 1, exportSchema = false)
public abstract class RoomDb extends RoomDatabase {

    private static RoomDb database;
    private static String DATABASE_NAME = "database";

    public abstract CityDao cityDao();

    public synchronized static RoomDb getInstance(Context context) {
        if (database == null) {
            database = Room.databaseBuilder(context.getApplicationContext(), RoomDb.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return database;
    }
}
