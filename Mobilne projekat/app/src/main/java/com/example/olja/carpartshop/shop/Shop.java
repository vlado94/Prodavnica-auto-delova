package com.example.olja.carpartshop.shop;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.olja.carpartshop.address.Address;
import com.example.olja.carpartshop.carBrand.CarBrand;
import com.example.olja.carpartshop.carPart.CarPart;

import java.util.List;

/**
 * Created by Olja on 6/7/2018.
 */

public class Shop implements Parcelable {

    private int ID;
    private String Name;
    //private Image image;
    private String Phone;

    private String Address;

    private double Longitude;

    private double Latitude;

    private List<CarBrand> CarBrands;
    private List<CarPart> CarParts;

    private boolean IsDeleted;
    private int UserId = 0;

    public Shop(Parcel in) {
        ID = in.readInt();
        Name = in.readString();
        Phone = in.readString();
        Address = in.readString();
        Longitude = in.readDouble();
        Latitude = in.readDouble();
    }
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(ID);
        dest.writeString(Name);
        dest.writeString(Phone);
        dest.writeString(Address);
        dest.writeDouble(Longitude);
        dest.writeDouble(Latitude);
    }
    public Shop(){

    }

    public static final Parcelable.Creator<Shop> CREATOR = new Parcelable.Creator<Shop>()
    {
        public Shop createFromParcel(Parcel in)
        {
            return new Shop(in);
        }
        public Shop[] newArray(int size)
        {
            return new Shop[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
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

    public List<CarPart> getCarParts() {
        return CarParts;
    }

    public void setCarParts(List<CarPart> carParts) {
        CarParts = carParts;
    }

    public double getLongitude() {
        return Longitude;
    }

    public double getLatitude() {
        return Latitude;
    }

    public boolean isDeleted() {
        return IsDeleted;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public void setDeleted(boolean deleted) {
        IsDeleted = deleted;
    }
}
