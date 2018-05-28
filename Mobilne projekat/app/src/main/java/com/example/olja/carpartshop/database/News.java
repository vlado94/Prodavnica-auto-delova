package com.example.olja.carpartshop.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

/**
 * Created by Olja on 5/28/2018.
 */
@Entity(tableName = "news")
public class News {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String shortDescription;
    private String longDescription;
    private Date pubishDate;

    @Ignore
    public News(String shortDescription, String longDescription, Date pubishDate) {
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.pubishDate = pubishDate;

    }

    @Ignore
    public News(int id, String shortDescription, String longDescription) {
        this.id = id;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.pubishDate = null;
    }
    public News(int id, String shortDescription, String longDescription, Date pubishDate) {
        this.id = id;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.pubishDate = pubishDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public Date getPubishDate() {
        return pubishDate;
    }

    public void setPubishDate(Date updatedAt) {
        this.pubishDate = pubishDate;
    }

}
