package com.example.android.newsapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        // Create an ArrayList of articles
        final ArrayList<Article> Articles = new ArrayList<>();
        Articles.add(new Article("1", "2", "3", "4"));
        Articles.add(new Article("5", "6", "7", "8"));

        // Create a PlaceAdapter, whose data source is a list of Places, especially restaurants in this activity.
        // The adapter knows how to create list item views for each item in the list.
        ArticleAdapter articlesAdapter = new ArticleAdapter(this, Articles);

        // Get a reference to the ListView, and attach the adapter to the listView.
        final ListView articlesView = findViewById(R.id.listview_articles);
        articlesView.setAdapter(articlesAdapter);

    }
}
