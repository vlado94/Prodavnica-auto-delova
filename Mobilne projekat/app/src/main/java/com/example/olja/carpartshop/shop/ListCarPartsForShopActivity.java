package com.example.olja.carpartshop.shop;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.olja.carpartshop.Constants;
import com.example.olja.carpartshop.R;
import com.example.olja.carpartshop.carBrand.CarBrand;
import com.example.olja.carpartshop.carPart.CarPart;
import com.example.olja.carpartshop.carPart.CarPartAdapter;
import com.example.olja.carpartshop.carPart.CarPartInformationsActivity;
import com.example.olja.carpartshop.database.DataAccess;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

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
 * Created by Olja on 6/9/2018.
 */

public class ListCarPartsForShopActivity extends AppCompatActivity  implements CarPartAdapter.CarPartOnClickHandler{
    private RecyclerView carPartRecyclerView;
    private CarPartAdapter cartPartAdapter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.car_parts_for_shop);

/*
        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Prodavnice");
        }

*/

        carPartRecyclerView =  findViewById(R.id.carPartRecyclerView);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        carPartRecyclerView.setLayoutManager(layoutManager);
        carPartRecyclerView.setHasFixedSize(true);
        cartPartAdapter = new CarPartAdapter(this,this);
        carPartRecyclerView.setAdapter(cartPartAdapter);

        getSupportActionBar().setTitle("Prodavnica auto delova");

        Intent intentThatStartedThisActivity = getIntent();
        if (intentThatStartedThisActivity.hasExtra("shopId")) {
            int id = intentThatStartedThisActivity.getIntExtra("shopId", -1);
            new ListCarPartsForShopActivity.GetCarPartsForShopTask().execute(id);
        }else {

            Bundle bundle = getIntent().getExtras();
            List<CarPart> carParts = bundle.getParcelableArrayList("data");
           //carParts = populateCarPartsWithReferences(carParts);

            cartPartAdapter.setCarParts(carParts);
        }

    }
   /* private List<CarPart> populateCarPartsWithReferences(List<CarPart> carParts){
        for (CarPart carPart: carParts) {
            //JSONArray json = DataAccess.sendGetResultList("CarPart", "Search", params[0]);
            HashMap<String, String> map = new HashMap<String,String>();
            HashMap<String, String> map2 = new HashMap<String,String>();
            map.put("id", String.valueOf(carPart.getShopID()));
            map2.put("id", String.valueOf(carPart.getCarBrandID()));
            JSONObject shop = DataAccess.sendGet("Shop", "Get", map );
            JSONObject carBrand = DataAccess.sendGet("CarBrand", "Get", map2 );

            Shop shopResult = new Gson().fromJson(shop.toString(), new TypeToken<Shop>(){}.getType());
            CarBrand carBrandResult = new Gson().fromJson(carBrand.toString(), new TypeToken<CarBrand>(){}.getType());

            carPart.setCarBrand(carBrandResult);
            carPart.setShop(shopResult);
        }

        return carParts;
    }*/



    public class GetCarPartsForShopTask extends AsyncTask<Integer, Void, List<CarPart>> {

        @Override
        protected List<CarPart> doInBackground(Integer... params) {
            try {
                int id = params[0];
                Uri.Builder builder = new Uri.Builder();
                builder.scheme("http");
                builder.encodedAuthority(Constants.url);

                builder.appendPath("Shop")
                        .appendPath("FindCarParts")
                        .appendQueryParameter("id",String.valueOf(id));
                String myUrl = builder.build().toString();
                URL weatherQueryUrl = null;

                try {
                    weatherQueryUrl = new URL(myUrl);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                String jsonWeatherResponse = getResponseFromHttpUrl(weatherQueryUrl);
                return getCarPartsFromJson(jsonWeatherResponse);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<CarPart> list) {
            cartPartAdapter.setCarParts(list);
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

    private List<CarPart> getCarPartsFromJson(String json){
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<CarPart>>(){}.getType();
        return gson.fromJson(json,listType);

    }

    @Override
    public void onClick(int carPartId) {
        Context context = this;
        Class destinationActivity = CarPartInformationsActivity.class;
        Intent startChildActivityIntent = new Intent(context, destinationActivity);
        startChildActivityIntent.putExtra("carPartId",carPartId);
        startActivity(startChildActivityIntent);
    }


}
