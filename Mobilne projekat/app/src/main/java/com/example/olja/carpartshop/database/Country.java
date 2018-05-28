package com.example.olja.carpartshop.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Olja on 5/28/2018.
 */
@Entity
public class Country {

    @PrimaryKey(autoGenerate = true)
    public int id;
    private String name;

    public Country(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Ignore
    public Country( String name) {
        this.name = name;
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
}
