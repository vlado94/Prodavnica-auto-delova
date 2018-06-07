package com.example.olja.carpartshop.country;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Olja on 5/28/2018.
 */

public class Country {


    public int ID;
    private String Name;
    private boolean IsDeleted;


    public Country(){}

    public Country( String name, boolean isDeleted) {
        this.Name = name;
        this.IsDeleted = isDeleted;

    }

    public Country(int id, String name, boolean isDeleted) {
        this.ID = id;
        this.Name = name;
        this.IsDeleted = isDeleted;

    }


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public boolean getIsDeleted() {
        return IsDeleted;
    }

    public void setIsDeleted(boolean deleted) {
        IsDeleted = deleted;
    }
}
