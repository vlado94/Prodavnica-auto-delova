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
    private String title;
    private String shortDescription;
    private String longDescription;
    private Date pubishDate;

@Ignore
    public News(){}

    @Ignore
    public News(String shortDescription, String longDescription, Date pubishDate, String title) {
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.pubishDate = pubishDate;
        this.title = title;

    }

    @Ignore
    public News(int id, String shortDescription, String longDescription, String title) {
        this.id = id;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.pubishDate = null;
        this.title = title;
    }
    public News(int id, String shortDescription, String longDescription, Date pubishDate, String title) {
        this.id = id;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.pubishDate = pubishDate;
        this.title = title;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
