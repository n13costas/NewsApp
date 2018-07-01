package com.example.android.newsapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class ArticleAdapter extends ArrayAdapter<Article> {

    public ArticleAdapter(Activity context, ArrayList<Article> Articles) {
        super(context, 0, Articles);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        // Get the article object located at this position in the list
        final Article currentArticle = getItem(position);

        // Fill the listview with appropriate texts
        TextView articleSection = listItemView.findViewById(R.id.news_section);
        articleSection.setText(currentArticle.getArticleSection());

        TextView articleTitle = listItemView.findViewById(R.id.news_title);
        articleTitle.setText(currentArticle.getArticleTitle());

        TextView articleAuthor =  listItemView.findViewById(R.id.news_author);
        articleAuthor.setText(currentArticle.getArticleAuthor());

        TextView articleDate =  listItemView.findViewById(R.id.news_date);
        articleDate.setText(currentArticle.getArticleDate());

        // Return the whole list item layout (containing 2-4 TextViews)
        // so that it can be shown in the ListView
        return listItemView;
    }

}
