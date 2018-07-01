package com.example.android.newsapp;

import android.text.TextUtils;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public final class QueryUtils {

    public static final String LOG_TAG = QueryUtils.class.getSimpleName();

    private QueryUtils() {

    }

    //Return the app data as a list if found
    public static List<Article> getArticleData(String apiUrl) {

        URL url = testValidUrl(apiUrl);

        String response = "";

        try {
            response = makeHttpRequest(url);
        }
        catch (IOException e) {
            Log.e(LOG_TAG, "Could not complete the HTTP request", e);
        }

        List<Article> articlesList = getJSONFields(response);

        return articlesList;
    }

    //Try the url
    private static URL testValidUrl(String testUrl) {

        URL url = null;

        try {
            url = new URL(testUrl);
        }
        catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Not a valid URL", e);
        }

        return url;
    }

    //Make the HTTP connection
    private static String makeHttpRequest(URL url) throws IOException {

        String jsonResponse = "";
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.connect();

            if ( urlConnection.getResponseCode() == 200 ) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = convertStreamToString(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        }
        catch (IOException e) {
            Log.e(LOG_TAG, "Could not connect to the API", e);
        }
        finally {
            if ( urlConnection != null ) {
                urlConnection.disconnect();
            }

            if ( inputStream != null ) {
                inputStream.close();
            }
        }

        return jsonResponse;
    }

    //Get the appropriate data from json response and fill the article object
    private static List<Article> getJSONFields(String JSONResponse) {

        if (TextUtils.isEmpty(JSONResponse)) {
            return null;
        }

        List<Article> articlesList = new ArrayList<>();

        try {
            JSONObject baseJsonObject = new JSONObject(JSONResponse);

            JSONObject response = baseJsonObject.getJSONObject("response");

            JSONArray result = response.getJSONArray("results");

            for (int i = 0; i < result.length(); i++) {
                JSONObject newsObject = result.getJSONObject(i);
                String section = newsObject.getString("webArticle");
                String title = newsObject.getString("webTitle");
                String webUrl = newsObject.getString("webUrl");
                String author = "N/A";
                String[] authorsArray = new String[]{};
                List<String> authorsList = new ArrayList<>();

                JSONArray tagsArray = newsObject.getJSONArray("tags");

                for (int j = 0; j < tagsArray.length(); j++) {
                    JSONObject tagsObject = tagsArray.getJSONObject(j);
                    String firstName = tagsObject.optString("firstName");
                    String lastName = tagsObject.optString("lastName");
                    String authorName;
                    if (TextUtils.isEmpty(firstName)) {
                        authorName = lastName;
                    } else {
                        authorName = firstName + " " + lastName;
                    }
                    authorsList.add(authorName);
                }

                if (authorsList.size() == 0) {
                    author = "N/A";
                } else {
                    author = TextUtils.join(", ", authorsList);
                }

                Article article = new Article(section, title, author, webUrl);
                articlesList.add(article);
            }
        }
        catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the news JSON results", e);
        }

        return articlesList;
    }

    private static String convertStreamToString(InputStream inputStream) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return sb.toString();
    }

}
