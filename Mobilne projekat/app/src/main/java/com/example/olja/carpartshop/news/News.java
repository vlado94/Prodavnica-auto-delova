package com.example.olja.carpartshop.news;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.example.olja.carpartshop.shop.Shop;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Olja on 5/28/2018.
 */

public class News {


    private int ID;
    private String Title;
    private String ShortDescription;
    private String LongDescription;
    private Date PubishDate;
    private boolean IsDeleted;
    private byte[] Image;


    public News(){}


    public News(String shortDescription, String longDescription, Date pubishDate, String title, boolean isDeleted, byte[] Image) {
        this.ShortDescription = shortDescription;
        this.LongDescription = longDescription;
        this.PubishDate = pubishDate;
        this.Title = title;
        this.IsDeleted = isDeleted;
        this.Image = Image;

    }

    @Ignore
    public News(int id, String shortDescription, String longDescription, String title, boolean isDeleted) {
        this.ID = id;
        this.ShortDescription = shortDescription;
        this.LongDescription = longDescription;
        this.PubishDate = null;
        this.Title = title;
        this.IsDeleted = isDeleted;
    }
    public News(int id, String shortDescription, String longDescription, Date pubishDate, String title) {
        this.ID = id;
        this.ShortDescription = shortDescription;
        this.LongDescription = longDescription;
        this.PubishDate = pubishDate;
        this.Title = title;
    }

    public News(NewsDTO dto) {
        this.ID = dto.getID();
        this.ShortDescription = dto.getShortDescription();
        this.LongDescription = dto.getLongDescription();

        String[] left = dto.getPubishDate().split("\\(");
        String[] right =  left[1].split("\\)");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(right[0]));
        Date dateUtil = calendar.getTime();
        Image = dto.getImage();
        this.PubishDate = dateUtil;
        this.Title = dto.getTitle();
        this.IsDeleted = dto.isDeleted();
    }

    public int getID() {
        return ID;
    }

    public void setID(int id) {
        this.ID = id;
    }

    public String getShortDescription() {
        return this.ShortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.ShortDescription = shortDescription;
    }

    public String getLongDescription() {
        return LongDescription;
    }

    public void setLongDescription(String longDescription) {
        this.LongDescription = longDescription;
    }

    public Date getPubishDate() {
        return PubishDate;
    }

    public void setPubishDate(Date updatedAt) {
        this.PubishDate = updatedAt;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        this.Title = title;
    }

    public byte[] getImage() {
        return Image;
    }

    public void setImage(byte[] image) {
        Image = image;
    }
}
