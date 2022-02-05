package com.example.newsapi.DB;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.newsapi.Models.NewzzHeadlines;

import java.util.List;

@Dao
public interface DAO {

   /* @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<User> listNewsHeadLines);*/
    @Insert
    void insertNew(User users);

    @Query("SELECT * FROM User_table")
    List<User> getUsers();

    @Query("SELECT * FROM User_table ORDER BY Uid DESC" )
    LiveData<List<User>> getAllUser();

    @Query("DELETE FROM User_table")
    void deleteAll();

}

