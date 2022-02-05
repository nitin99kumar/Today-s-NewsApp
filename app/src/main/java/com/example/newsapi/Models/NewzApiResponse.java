package com.example.newsapi.Models;

import androidx.room.Entity;

import java.util.List;

public class NewzApiResponse {

    String status;
    int totalResults;
    List<NewzzHeadlines> articles;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public List<NewzzHeadlines> getArticles() {
        return articles;
    }

    public void setArticles(List<NewzzHeadlines> articles) {
        this.articles = articles;
    }


}
