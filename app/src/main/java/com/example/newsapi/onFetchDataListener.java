package com.example.newsapi;

import com.example.newsapi.Models.NewzzHeadlines;

import java.util.List;

public interface onFetchDataListener<NewzApiResponse> {

    void onFetchData(List<NewzzHeadlines> list, String message);
    void onError(String message);

}
