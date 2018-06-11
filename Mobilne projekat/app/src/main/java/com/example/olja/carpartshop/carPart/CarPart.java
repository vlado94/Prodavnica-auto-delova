package com.example.olja.carpartshop.carPart;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.olja.carpartshop.carBrand.CarBrand;
import com.example.olja.carpartshop.shop.Shop;


import java.util.Date;

/**
 * Created by Olja on 5/29/2018.
 */

public class CarPart implements Parcelable {


    private int ID;
    private String Name;
    //public byte[] Image { get; set; }
    private double Price;
    private String ShortDescription;
    private String LongDescription;
    private int quantity;
    private int visitsNumber;
    private Date publishDate;
    private int CarBrandID;
    private CarBrand CarBrand;

    private int ShopID;
    private Shop Shop;

    private int UserID = 0;

    private boolean IsDeleted;

    public  CarPart(){

    }
    public CarPart(int ID, String name, double price, String shortDescription, String longDescription, int quantity, int visitsNumber, Date publishDate, int ipAddresses, int carBrandID, com.example.olja.carpartshop.carBrand.CarBrand carBrand, int shopID, Shop shop, boolean isDeleted) {
        this.ID = ID;
        Name = name;
        Price = price;
        ShortDescription = shortDescription;
        LongDescription = longDescription;
        this.quantity = quantity;
        this.visitsNumber = visitsNumber;
        this.publishDate = publishDate;
        CarBrandID = carBrandID;
        CarBrand = carBrand;
        ShopID = shopID;
        this.Shop = shop;
        IsDeleted = isDeleted;
    }

    public CarPart(Parcel in) {
        ID = in.readInt();
        Name = in.readString();
        Price = in.readDouble();
        ShortDescription = in.readString();
        LongDescription = in.readString();
        quantity = in.readInt();
         visitsNumber = in.readInt();
        //PublishDate = in.read
        CarBrandID = in.readInt();
        ShopID = in.readInt();

    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(ID);
        dest.writeString(Name);
        dest.writeDouble(Price);
        dest.writeString(ShortDescription);
        dest.writeString(LongDescription);
        dest.writeInt(quantity);
        dest.writeInt(visitsNumber);
        dest.writeInt(CarBrandID);
        dest.writeInt(ShopID);

    }


    public static final Parcelable.Creator<CarPart> CREATOR = new Parcelable.Creator<CarPart>()
    {
        public CarPart createFromParcel(Parcel in)
        {
            return new CarPart(in);
        }
        public CarPart[] newArray(int size)
        {
            return new CarPart[size];
        }
    };


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

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public String getShortDescription() {
        return ShortDescription;
    }

    public void setShortDescription(String shortDescription) {
        ShortDescription = shortDescription;
    }

    public String getLongDescription() {
        return LongDescription;
    }

    public void setLongDescription(String longDescription) {
        LongDescription = longDescription;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getVisitsNumber() {
        return visitsNumber;
    }

    public void setVisitsNumber(int visitsNumber) {
        this.visitsNumber = visitsNumber;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public int getCarBrandID() {
        return CarBrandID;
    }

    public void setCarBrandID(int carBrandID) {
        CarBrandID = carBrandID;
    }

    public com.example.olja.carpartshop.carBrand.CarBrand getCarBrand() {
        return CarBrand;
    }

    public void setCarBrand(com.example.olja.carpartshop.carBrand.CarBrand carBrand) {
        CarBrand = carBrand;
    }

    public int getShopID() {
        return ShopID;
    }

    public void setShopID(int shopID) {
        ShopID = shopID;
    }

    public Shop getShop() {
        return Shop;
    }

    public void setShop(Shop shop) {
        this.Shop = shop;
    }

    public void setIsDeleted(boolean isDeleted){
        IsDeleted = isDeleted;
    }
    public boolean getIsDeleted(){
        return IsDeleted;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }
}