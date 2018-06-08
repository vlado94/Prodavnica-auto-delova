package com.example.olja.carpartshop.services;

import android.app.IntentService;
import android.content.Intent;
import android.net.Uri;

import com.example.olja.carpartshop.Constants;
import com.example.olja.carpartshop.address.Address;
import com.example.olja.carpartshop.carBrand.CarBrand;
import com.example.olja.carpartshop.carPart.CarPart;
import com.example.olja.carpartshop.city.City;
import com.example.olja.carpartshop.country.Country;
import com.example.olja.carpartshop.news.News;
import com.example.olja.carpartshop.shop.Shop;
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

    private List<Country> listCountries;
    private List<City> listCities;
    private List<News> listNews;
    private List<Address> listAddresses;
    private List<CarBrand> carBrands;
    private List<Shop> shops;
    private List<CarPart> carParts;

    public getFromLinkIntentService() {
        super("getFromLinkIntentService");

    }

    @Override
    protected void onHandleIntent(Intent intent) {
        listCountries = Constants.getItemsFromServer("Country","GetAll");
        listCities = Constants.getItemsFromServer("City","GetAll");
        listAddresses = Constants.getItemsFromServer("Address","GetAll");
        carBrands = Constants.getItemsFromServer("CarBrand","GetAll");
        shops = Constants.getItemsFromServer("Shop","GetAll");
        carParts = Constants.getItemsFromServer("CarPart","GetAll");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
