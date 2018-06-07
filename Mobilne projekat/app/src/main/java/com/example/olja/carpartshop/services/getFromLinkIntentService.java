package com.example.olja.carpartshop.services;

import android.app.IntentService;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.example.olja.carpartshop.Executor;
import com.example.olja.carpartshop.MainActivity;
import com.example.olja.carpartshop.database.CarPartDatabase;
import com.example.olja.carpartshop.database.Country;
import com.example.olja.carpartshop.database.News;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Olja on 6/4/2018.
 */

public class getFromLinkIntentService extends IntentService {
    private CarPartDatabase database;
    public getFromLinkIntentService() {
        super("getFromLinkIntentService");

    }

    @Override
    protected void onHandleIntent(Intent intent) {

        try {
            String host = "192.168.0.12:52387";
            Uri.Builder builder = new Uri.Builder();
            builder.scheme("http");
            builder.encodedAuthority(host);

            builder.appendPath("Country")
                    .appendPath("GetAll");
            String myUrl = builder.build().toString();
            URL weatherRequestUrl = null;
            try {
                URL weatherQueryUrl = new URL(myUrl);
                Log.v("dsada", "URL: " + weatherQueryUrl);
                weatherRequestUrl= weatherQueryUrl;
            } catch (MalformedURLException e) {
                e.printStackTrace();

            }

            String jsonWeatherResponse = getResponseFromHttpUrl(weatherRequestUrl);

           // Country data =new Country();
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<Country>>(){}.getType();
            List<Country> data = gson.fromJson(jsonWeatherResponse,listType);

            Log.d("TITLE", "asfasssfsfaaaaaaaaa"+data.get(0).getName());
            addNews(data);
        } catch (Exception e) {
            /* Server probably invalid */
            e.printStackTrace();
        }

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

    @Override
    public void onDestroy() {
        super.onDestroy();
        database = CarPartDatabase.getInstance(getApplicationContext());
    }

    private void addNews(List<Country> data) {

       for (final Country item:data) {
           final Country newCountry =  mappingCountry(item);
           Executor.getInstance().diskIO().execute(new Runnable() {
               // @Override
               public void run() {
                  // database.countryDao().insertCountry(newCountry);
                   // database.newsDao().insertNews(second);
                   //finish();
               }
           });
        }


    }

    private Country mappingCountry(Country country){
        Country newCountry = new Country(country.getName(), country.getIsDeleted());
        return newCountry;

    }

}
