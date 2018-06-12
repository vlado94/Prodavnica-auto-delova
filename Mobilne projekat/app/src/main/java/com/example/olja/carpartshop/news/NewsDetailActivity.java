package com.example.olja.carpartshop.news;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.olja.carpartshop.Constants;
import com.example.olja.carpartshop.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Olja on 5/25/2018.
 */

public class NewsDetailActivity extends AppCompatActivity {


    private TextView newsTitle;
    //private TextView newsDate;
    private TextView newsContent;
    private android.support.constraint.ConstraintLayout layout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_detail);
        newsTitle = (TextView) findViewById(R.id.newsTitle);
        //newsDate = (TextView) findViewById(R.id.newsDate);
        newsContent = (TextView) findViewById(R.id.newsContent);

        layout = (android.support.constraint.ConstraintLayout) findViewById(R.id.news_details_layout);

        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Detalji vesti");
        }
        Intent intentThatStartedThisActivity = getIntent();
        if (intentThatStartedThisActivity.hasExtra("newsId")) {
            int id = intentThatStartedThisActivity.getIntExtra("newsId", -1);
           new GetNewsByIdTask().execute(id);
        }
        defaultSetup();
    }

    private void defaultSetup() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        String str = sp.getString("defaultbackground", "temp");
        if(str.equals("greenback")) {
            layout.setBackgroundResource(R.drawable.greenback);
        }
        else if(str.equals("whitepic")) {
            layout.setBackgroundResource(R.drawable.whitepic);
        } else {
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("defaultbackground", "whitepic");
            editor.apply();
            layout.setBackgroundResource(R.drawable.whitepic);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }

    public class GetNewsByIdTask extends AsyncTask<Integer, Void, News> {


        @Override
        protected News doInBackground(Integer... params) {
            try {

                int id = params[0];
                Uri.Builder builder = new Uri.Builder();
                builder.scheme("http");
                builder.encodedAuthority(Constants.url);

                builder.appendPath("News")
                        .appendPath("Get")
                        .appendQueryParameter("id",String.valueOf(id));
                String myUrl = builder.build().toString();
                URL weatherQueryUrl = null;

                try {
                    weatherQueryUrl = new URL(myUrl);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                String jsonWeatherResponse = getResponseFromHttpUrl(weatherQueryUrl);
                return getOneNewsFromJsom(jsonWeatherResponse);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onPostExecute(News list) {
            newsTitle.setText(list.getTitle());
            newsContent.setText(list.getLongDescription());

        }
    }


    @Override
    protected void onResume() {
        defaultSetup();
        super.onResume();

    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        String response = null;
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();

            if (hasInput) {
                response = scanner.next();
            }
            scanner.close();
            return response;
        } finally {
            urlConnection.disconnect();
        }
    }

    private News getOneNewsFromJsom(String json) {
        Gson gson = new Gson();
        Type listType = new TypeToken<NewsDTO>() {}.getType();
        NewsDTO newsDTOS = gson.fromJson(json, listType);
        News news =  new News(newsDTOS);
        return news;

    }
}