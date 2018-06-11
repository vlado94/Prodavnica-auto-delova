package com.example.olja.carpartshop.shop;

import com.example.olja.carpartshop.address.Address;
import com.example.olja.carpartshop.carBrand.CarBrand;

import java.util.List;

/**
 * Created by Olja on 6/7/2018.
 */

public class Shop {

    private int ID;
    private String Name;
    //private Image image;
    private String Phone;

    private String Address;

    private List<CarBrand> CarBrands;
    private boolean IsDeleted;
    private int UserId = 0;

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

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }



    public List<CarBrand> getCarBrands() {
        return CarBrands;
    }

    public void setCarBrands(List<CarBrand> carBrands) {
        CarBrands = carBrands;
    }

    public boolean getIsDeleted() {
        return IsDeleted;
    }

    public void setIsDeleted(boolean deleted) {
        IsDeleted = deleted;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }
}
