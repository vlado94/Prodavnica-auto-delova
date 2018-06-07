package com.example.olja.carpartshop.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

/**
 * Created by Olja on 5/29/2018.
 */

public class CarPart {


    private int id;
    public String name;
    //public byte[] Image { get; set; }
    public double price;
    public String shortDescription;
    public String longDescription;
    public int quantity;
    public int visitsNumber;
    public Date publishDate;
    public int ipAddresses;


   // public ICollection<Category> Categories { get; set; }
    //public ICollection<CarBrand> CarBrands { get; set; }
}
