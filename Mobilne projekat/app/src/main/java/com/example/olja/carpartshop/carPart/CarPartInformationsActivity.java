package com.example.olja.carpartshop.carPart;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import android.widget.ImageView;
import android.widget.TextView;


import com.example.olja.carpartshop.Constants;
import com.example.olja.carpartshop.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.CacheRequest;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Olja on 6/11/2018.
 */

public class CarPartInformationsActivity  extends AppCompatActivity {

    private TextView longDesc;
    private ImageView icon;
    private TextView name;
    private TextView shortDescription;
    private TextView price;
    private TextView shopName;
    private TextView quantity;
    private TextView carBrand;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.car_part_details);
        longDesc = (TextView) findViewById(R.id.car_part_details_longDesc);
        icon = (ImageView) findViewById(R.id.icon);
        name =  (TextView) findViewById(R.id.car_part_details_name);
        shortDescription =  (TextView) findViewById(R.id.car_part_details_short_desc);
        price =  (TextView) findViewById(R.id.car_part_details_price);
        shopName = (TextView) findViewById(R.id.car_part_details_shopName);
        quantity = (TextView) findViewById(R.id.car_part_details_quantity);
        carBrand = (TextView) findViewById(R.id.car_part_details_carBrand);


        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Detalji proizvoda");
        }

        Intent intentThatStartedThisActivity = getIntent();
        if (intentThatStartedThisActivity.hasExtra("carPartId")) {
            int id = intentThatStartedThisActivity.getIntExtra("carPartId", -1);

            new CarPartInformationsActivity.GetCarPartByIdTask(this).execute(id);
        }

    }
    public class GetCarPartByIdTask extends AsyncTask<Integer, Void, CarPart> {

        private Context mContext;
        public GetCarPartByIdTask (Context context){
            mContext = context;
        }
        @Override
        protected CarPart doInBackground(Integer... params) {
            try {

                int id = params[0];
                Uri.Builder builder = new Uri.Builder();
                builder.scheme("http");
                builder.encodedAuthority(Constants.url);

                builder.appendPath("CarPart")
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
                CarPart carPart = getShopFromJsom(jsonWeatherResponse);
                return carPart;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onPostExecute(CarPart carPart) {
            longDesc.setText(carPart.getLongDescription());
            //icon.set
            name.setText(carPart.getName());
            shortDescription.setText(carPart.getShortDescription());
            price.setText(String.valueOf(carPart.getPrice()) + " RSD");
            shopName.setText(carPart.getShop().getName());
            quantity.setText(String.valueOf(carPart.getQuantity()));
            carBrand.setText(carPart.getCarBrand().getName());

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

    private CarPart getShopFromJsom(String json) {
        Gson gson = new Gson();
        Type listType = new TypeToken<CarPart>() {}.getType();
        return gson.fromJson(json, listType);
    }
}
