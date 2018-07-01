package com.example.android.newsapp;

import android.content.AsyncTaskLoader;
import android.content.Context;
import java.util.List;

public class ArticleLoader extends AsyncTaskLoader<List<Article>> {

    public static final String LOG_TAG = ArticleLoader.class.getSimpleName();

    private String mUrl;

    public ArticleLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    // Call a worker thread to perform the actual load and to return the result of the load operation
    @Override
    public List<Article> loadInBackground() {

        if ( mUrl == null ) {
            return null;
        }

        List<Article> articlesList = QueryUtils.getArticleData(mUrl);

        return articlesList;
    }

}
