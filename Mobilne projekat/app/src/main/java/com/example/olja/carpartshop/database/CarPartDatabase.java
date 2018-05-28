package com.example.olja.carpartshop.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.util.Log;

/**
 * Created by Olja on 5/28/2018.
 */

@Database(entities = {News.class, Country.class, City.class}, version = 1, exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class CarPartDatabase extends RoomDatabase {
    private static final String LOG_TAG = CarPartDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "todolist";
    private static CarPartDatabase sInstance;

    public static CarPartDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                Log.d(LOG_TAG, "Creating new database instance");
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        CarPartDatabase.class, CarPartDatabase.DATABASE_NAME)
                        .build();

            }
        }
        Log.d(LOG_TAG, "Getting the database instance");
        return sInstance;
    }

    public abstract NewsDao newsDao();
}
