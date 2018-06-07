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
public interface CountryDao {

    @Query("SELECT * FROM country") // ORDER BY priority
    LiveData<List<Country>> loadAllCountries();

    @Insert
    void insertCountry(Country country);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateCountry(Country country);

    @Delete
    void deleteCountry(Country country);

    @Query("SELECT * FROM country WHERE id = :id")
    LiveData<Country> loadCountryById(int id);
}
