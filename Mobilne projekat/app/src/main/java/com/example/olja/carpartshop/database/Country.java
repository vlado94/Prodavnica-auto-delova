package com.example.olja.carpartshop.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Olja on 5/28/2018.
 */
@Entity
public class Country {

    @PrimaryKey(autoGenerate = true)
    public int ID;
    private String Name;
    private boolean IsDeleted;


    public Country(int id, String name) {
        this.ID = id;
        this.Name = name;

    }

    public Country(){}
    @Ignore
    public Country( String name, boolean isDeleted) {
        this.Name = name;
        this.IsDeleted = isDeleted;

    }



    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public boolean isDeleted() {
        return IsDeleted;
    }

    public void setIsDeleted(boolean deleted) {
        IsDeleted = deleted;
    }

    public boolean getIsDeleted(){
        return isDeleted();
    }
}
