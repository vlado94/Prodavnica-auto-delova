package com.example.olja.carpartshop.carPart;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.olja.carpartshop.AddProductActivity;
import com.example.olja.carpartshop.Constants;
import com.example.olja.carpartshop.MainActivity;
import com.example.olja.carpartshop.R;
import com.example.olja.carpartshop.database.DataAccess;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.CacheRequest;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Olja on 6/11/2018.
 */

public class CarPartInformationsActivity  extends AppCompatActivity implements View.OnClickListener {

    private TextView longDesc;
    private ImageView icon;
    private TextView name;
    private TextView shortDescription;
    private TextView price;
    private TextView shopName;
    private TextView quantity;
    private TextView carBrand;
    private ImageView notifyImg;
    private ImageView callImg;
    private int id = 0;
    private String phoneNumber;

    private android.support.constraint.ConstraintLayout layout;

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
        notifyImg = (ImageView) findViewById(R.id.notifiy_img);
        callImg = (ImageView) findViewById(R.id.car_part_details_callIcon);


        notifyImg.setOnClickListener(this);
        callImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callPhoneNumber(phoneNumber);
            }
        });
        layout = (android.support.constraint.ConstraintLayout) findViewById(R.id.carpart_details_layout);




        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Detalji proizvoda");
        }

        Intent intentThatStartedThisActivity = getIntent();
        if (intentThatStartedThisActivity.hasExtra("carPartId")) {
            id = intentThatStartedThisActivity.getIntExtra("carPartId", -1);

            new CarPartInformationsActivity.GetCarPartByIdTask(this).execute(id);
        }

        if(Constants.getLoggedUser() == null || Constants.getLoggedUser().getID() == 0 )
            notifyImg.setVisibility(View.INVISIBLE);

        defaultSetup();
    }


    @Override
    protected void onResume() {
        defaultSetup();
        super.onResume();

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
    public void onClick(View view) {
        HashMap<String, String> parameters = new HashMap<String, String>();
        parameters.put("userID", Integer.toString(Constants.getLoggedUser().getID()));
        parameters.put("productID", Integer.toString(id));
        new PrepareToNotifyTask().execute(parameters);
    }


    public class PrepareToNotifyTask extends AsyncTask<HashMap, Void, JSONObject> {

        @Override
        protected JSONObject doInBackground(HashMap... part) {
            JSONObject json = DataAccess.sendGet("CarPart", "PrepareToNotify", part[0]);
            return  json;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            Toast.makeText(CarPartInformationsActivity.this, "Bicete obavesteni kada ovaj deo bude na stanju.", Toast.LENGTH_SHORT).show();

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
            phoneNumber = carPart.getShop().getPhone();
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

    public void callPhoneNumber(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 101);
        } else {
            this.startActivity(intent);

        }
    }
}
