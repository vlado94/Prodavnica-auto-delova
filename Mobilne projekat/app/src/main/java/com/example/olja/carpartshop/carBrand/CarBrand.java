package com.example.olja.carpartshop.carBrand;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Olja on 6/7/2018.
 */


public class CarBrand {


    private int ID;
    private String Name;
    private boolean IsDeleted;


    public CarBrand(){

    }

    public CarBrand(int ID, String name, boolean isDeleted) {
        this.ID = ID;
        this.Name = name;
        this.IsDeleted = isDeleted;
    }
    @Ignore
    public CarBrand(String name, boolean isDeleted) {
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
        this.IsDeleted = deleted;
    }


}
