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
public interface CityDao {

    @Query("SELECT * FROM city")
    LiveData<List<City>> loadAllCities();

    @Insert
    void insertCity(City city);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateCity(City city);

    @Delete
    void deleteCity(City city);

    @Query("SELECT * FROM city WHERE id = :id")
    LiveData<City> loadCityById(int id);

    @Query("SELECT * FROM city WHERE coutryId=:coutryId")
    List<City> findCitiesForCountry( int coutryId);
}
