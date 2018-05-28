package com.example.olja.carpartshop.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by Olja on 5/28/2018.
 */
@Dao
public interface NewsDao {

    @Query("SELECT * FROM news") // ORDER BY priority
    LiveData<List<News>> loadAllNews();

    @Insert
    void insertNews(News news);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateNews(News news);

    @Delete
    void deleteNews(News news);

    // COMPLETED (1) Wrap the return type with LiveData
    @Query("SELECT * FROM news WHERE id = :id")
    LiveData<News> loadNewsById(int id);
}
