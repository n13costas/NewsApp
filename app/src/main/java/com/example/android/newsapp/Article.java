package com.example.android.newsapp;

public class Article {

    // Declaring the private variables for the articles
    private String mSection;
    private String mTitle;
    private String mAuthor;
    private String mDate;

    public Article(String articleSection, String articleTitle, String articleAuthor, String articleDate)
    {
        mSection = articleSection;
        mTitle = articleTitle;
        mAuthor = articleAuthor;
        mDate = articleDate;
    }

    // Get the article section
    public String getArticleSection() {
        return mSection;
    }

    // Get the article title
    public String getArticleTitle() {
        return mTitle;
    }

    // Get the article author (if applicable)
    public String getArticleAuthor() {
        return mAuthor;
    }

    // Get the article published date (if applicable)
    public String getArticleDate() {
        return mDate;
    }

}
