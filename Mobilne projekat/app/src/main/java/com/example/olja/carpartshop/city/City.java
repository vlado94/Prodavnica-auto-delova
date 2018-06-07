package com.example.olja.carpartshop.city;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Olja on 5/28/2018.
 */


public class City {

    private int ID;
    private String Name;
    private int CountryId;
    private com.example.olja.carpartshop.country.Country Country;
    private boolean IsDeleted;

    public City(){}

    public City(int ID, String name, int countryId, com.example.olja.carpartshop.country.Country country, boolean isDeleted) {
        this.ID = ID;
        Name = name;
        CountryId = countryId;
        Country = country;
        IsDeleted = isDeleted;
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

    public int getCountryId() {
        return CountryId;
    }

    public void setCountryId(int countryId) {
        CountryId = countryId;
    }

    public com.example.olja.carpartshop.country.Country getCountry() {
        return Country;
    }

    public void setCountry(com.example.olja.carpartshop.country.Country country) {
        Country = country;
    }

    public boolean isDeleted() {
        return IsDeleted;
    }

    public void setDeleted(boolean deleted) {
        IsDeleted = deleted;
    }


}
