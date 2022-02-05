package com.example.newsapi.DB;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "User_table")
public class User {

    @PrimaryKey(autoGenerate = true)
    public int Uid;

    public String firstName;

    public String lastName;

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String ImageUrl;

    public User(String firstName, String lastName, String imageUrl) {
        this.firstName = firstName;
        this.lastName = lastName;
        ImageUrl = imageUrl;
    }
/*  @ColumnInfo(name = "image" , typeAffinity = ColumnInfo.BLOB)
    public byte[] ImagesData;

    public void setImagesData(byte[] imagesData) {
        ImagesData = imagesData;
    }

    public byte[] getImagesData() {
        return ImagesData;
    }*/

   /* public User(String firstName, String lastName, byte[] imagesData) {
        this.firstName = firstName;
        this.lastName = lastName;
        ImagesData = imagesData;
    }*/


    public User() { }

    public int getUid() {
        return Uid;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}