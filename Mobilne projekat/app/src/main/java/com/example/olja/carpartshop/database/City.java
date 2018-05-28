package com.example.olja.carpartshop.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/**
 * Created by Olja on 5/28/2018.
 */
@Entity(foreignKeys = @ForeignKey(entity = Country.class,
        parentColumns = "id",
        childColumns = "countryId",
        onDelete = CASCADE))
public class City {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    public int countryId;

    public City(int id, String name, int countryId) {
        this.id = id;
        this.name = name;
        this.countryId = countryId;
    }

    @Ignore
    public City(String name, int countryId) {
        this.name = name;
        this.countryId = countryId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCountryId( int countryId){
        this.countryId = countryId;

    }
    public int getCountryId(){
        return this.countryId;
    }


}
