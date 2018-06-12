package com.example.olja.carpartshop;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.olja.carpartshop.carPart.CarPart;
import com.example.olja.carpartshop.carPart.CarPartAdapter;
import com.example.olja.carpartshop.carPart.CarPartInformationsActivity;
import com.example.olja.carpartshop.database.DataAccess;
import com.example.olja.carpartshop.shop.ListCarPartsForShopActivity;
import com.example.olja.carpartshop.shop.Shop;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Nebojsa on 6/1/2018.
 */

public class HomeFragment extends Fragment implements CarPartAdapter.CarPartOnClickHandler {
    private static final String TAG = "HomeFragment";
    List<CarPart> result;
    private RecyclerView carPartRecyclerView;
    private CarPartAdapter cartPartAdapter;

    Context c = this.getContext();
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.car_parts_for_shop, container, false);

        carPartRecyclerView =  view.findViewById(R.id.carPartRecyclerView);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        carPartRecyclerView.setLayoutManager(layoutManager);
        carPartRecyclerView.setHasFixedSize(true);
        cartPartAdapter = new CarPartAdapter(this,getContext());
        carPartRecyclerView.setAdapter(cartPartAdapter);


        new HomeFragment.GetCarPartsByDateTask().execute("GetCarPartsByDateTask");
        return  view;


    }
    public class GetCarPartsByDateTask extends AsyncTask<String, Void, JSONArray> {

        @Override
        protected JSONArray doInBackground(String... params) {
                JSONArray json = DataAccess.sendGetResultList("CarPart", "GetAllByDate", new HashMap<String, String>());
                return  json;
        }


        @Override
        protected void onPostExecute(JSONArray jsonObject) {
            super.onPostExecute(jsonObject);

            if(jsonObject != null){
                result = new Gson().fromJson(jsonObject.toString(), new TypeToken<ArrayList<CarPart>>(){}.getType());
                cartPartAdapter.setCarParts(result);
            }

        }

    }
    @Override
    public void onClick(int carPartId) {
        Context context = getContext();
        Class destinationActivity = CarPartInformationsActivity.class;
        Intent startChildActivityIntent = new Intent(context, destinationActivity);
        startChildActivityIntent.putExtra("carPartId",carPartId);
        startActivity(startChildActivityIntent);
    }



}
