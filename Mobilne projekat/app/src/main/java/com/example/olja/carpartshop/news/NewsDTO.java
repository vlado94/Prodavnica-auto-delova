package com.example.olja.carpartshop.news;

import android.arch.persistence.room.Ignore;

import java.util.Date;

/**
 * Created by Olja on 6/8/2018.
 */

public class NewsDTO {


    private int ID;
    private String Title;
    private String ShortDescription;
    private String LongDescription;
    private String PubishDate;
    private boolean IsDeleted;


    public NewsDTO(){}


    public NewsDTO(String shortDescription, String longDescription, String pubishDate, String title, boolean isDeleted) {
        this.ShortDescription = shortDescription;
        this.LongDescription = longDescription;
        this.PubishDate = pubishDate;
        this.Title = title;
        this.IsDeleted = isDeleted;

    }

    @Ignore
    public NewsDTO(int id, String shortDescription, String longDescription, String title, boolean isDeleted) {
        this.ID = id;
        this.ShortDescription = shortDescription;
        this.LongDescription = longDescription;
        this.PubishDate = null;
        this.Title = title;
        this.IsDeleted = isDeleted;
    }
    public NewsDTO(int id, String shortDescription, String longDescription, String pubishDate, String title) {
        this.ID = id;
        this.ShortDescription = shortDescription;
        this.LongDescription = longDescription;
        this.PubishDate = pubishDate;
        this.Title = title;
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

    public String getPubishDate() {
        return PubishDate;
    }

    public void setPubishDate(String updatedAt) {
        this.PubishDate = updatedAt;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        this.Title = title;
    }

    public boolean isDeleted() {
        return IsDeleted;
    }

    public void setDeleted(boolean deleted) {
        IsDeleted = deleted;
    }
}
