package com.example.olja.carpartshop.services;

import android.app.IntentService;
import android.content.Intent;
import android.net.Uri;

import com.example.olja.carpartshop.address.Address;
import com.example.olja.carpartshop.city.City;
import com.example.olja.carpartshop.country.Country;
import com.example.olja.carpartshop.news.News;
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
 * Created by Olja on 6/4/2018.
 */

public class getFromLinkIntentService extends IntentService {

    private String host = "192.168.0.18:52387";

    private List<Country> listCountries;
    private List<City> listCities;
    private List<News> listNews;
    private List<Address> listAddresses;

    public getFromLinkIntentService() {
        super("getFromLinkIntentService");

    }

    @Override
    protected void onHandleIntent(Intent intent) {
            getCountriesFromServer();
            getCitiesFromServer();
            getAddressesFromServer();


    }

    @Override
    public void onDestroy() {
        super.onDestroy();


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




/* GetFromServer metode */
    private void getCountriesFromServer(){
        try {

            Uri.Builder builder = new Uri.Builder();
            builder.scheme("http");
            builder.encodedAuthority(host);

            builder.appendPath("Country")
                    .appendPath("GetAll");
            String myUrl = builder.build().toString();
            URL weatherQueryUrl = null;

            try {
                weatherQueryUrl = new URL(myUrl);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            String jsonWeatherResponse = getResponseFromHttpUrl(weatherQueryUrl);
            listCountries = getCountriesFromJsom(jsonWeatherResponse);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void getCitiesFromServer(){
        try {

            Uri.Builder builder = new Uri.Builder();
            builder.scheme("http");
            builder.encodedAuthority(host);

            builder.appendPath("City")
                    .appendPath("GetAll");
            String myUrl = builder.build().toString();
            URL weatherQueryUrl = null;

            try {
                weatherQueryUrl = new URL(myUrl);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            String jsonWeatherResponse = getResponseFromHttpUrl(weatherQueryUrl);
            listCities = getCitiesFromJsom(jsonWeatherResponse);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getAddressesFromServer(){
        try {

            Uri.Builder builder = new Uri.Builder();
            builder.scheme("http");
            builder.encodedAuthority(host);

            builder.appendPath("Address")
                    .appendPath("GetAll");
            String myUrl = builder.build().toString();
            URL weatherQueryUrl = null;

            try {
                weatherQueryUrl = new URL(myUrl);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            String jsonWeatherResponse = getResponseFromHttpUrl(weatherQueryUrl);
            listAddresses = getAddressesFromJsom(jsonWeatherResponse);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


/* GetFromJSON */
    private List<Country> getCountriesFromJsom(String json){
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<Country>>(){}.getType();
        List<Country> countries =  gson.fromJson(json,listType);
        return countries;
    }


    private List<City> getCitiesFromJsom(String json){
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<City>>(){}.getType();
        List<City> cities =  gson.fromJson(json,listType);


        return cities;
    }
    private List<Address> getAddressesFromJsom(String json){
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<Address>>(){}.getType();
        List<Address> addresses =  gson.fromJson(json,listType);


        return addresses;
    }




}
