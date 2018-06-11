package com.example.olja.carpartshop.address;

import android.arch.persistence.room.Entity;

import com.example.olja.carpartshop.city.City;

/**
 * Created by Olja on 6/7/2018.
 */


public class Address {

    private int ID;

    private String Street;

    private int Number;

    private int CityId;

    private City City;

    private boolean IsDeleted;

    public Address(){

    }

    public Address(int ID) {
        this.ID = ID;
    }

    public Address(int ID, String street, int number, int cityId, com.example.olja.carpartshop.city.City city, boolean isDeleted) {
        this.ID = ID;
        Street = street;
        Number = number;
        CityId = cityId;
        City = city;
        IsDeleted = isDeleted;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getStreet() {
        return Street;
    }

    public void setStreet(String street) {
        Street = street;
    }

    public int getNumber() {
        return Number;
    }

    public void setNumber(int number) {
        Number = number;
    }

    public int getCityId() {
        return CityId;
    }

    public void setCityId(int cityId) {
        CityId = cityId;
    }

    public com.example.olja.carpartshop.city.City getCity() {
        return City;
    }

    public void setCity(com.example.olja.carpartshop.city.City city) {
        City = city;
    }

    public boolean getIsDeleted() {
        return IsDeleted;
    }

    public void setIsDeleted(boolean deleted) {
        IsDeleted = deleted;
    }
}
