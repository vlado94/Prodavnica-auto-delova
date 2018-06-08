package com.example.olja.carpartshop.shop;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Telephony;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.olja.carpartshop.Constants;
import com.example.olja.carpartshop.R;
import com.example.olja.carpartshop.address.Address;
import com.example.olja.carpartshop.carBrand.CarBrand;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
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
 * Created by Olja on 6/8/2018.
 */

public class ShopDetailsActivity extends AppCompatActivity  {

    private GoogleMap mGoogleMap;
    private ListView listAddresses;
    private ListView carBrandsListView;

    private TextView shopNameDetails;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_details);

        shopNameDetails = (TextView) findViewById(R.id.shopNameDetails);
        listAddresses = (ListView) findViewById(R.id.addresesListView);
        carBrandsListView = (ListView) findViewById(R.id.carBrandsListView);

        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        Intent intentThatStartedThisActivity = getIntent();
        if (intentThatStartedThisActivity.hasExtra("shopId")) {
            int id = intentThatStartedThisActivity.getIntExtra("shopId", -1);
            new ShopDetailsActivity.GetShopByIdTask(this).execute(id);


        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.shop_map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                LatLng latLng = new LatLng(1.289545, 103.849972);
                googleMap.addMarker(new MarkerOptions().position(latLng)
                        .title("Singapore"));
              //  googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12.0f));
            }


        });

    }

    public class GetShopByIdTask extends AsyncTask<Integer, Void, Shop> {
        private Context mContext;
        public GetShopByIdTask (Context context){
            mContext = context;
        }
        @Override
        protected Shop doInBackground(Integer... params) {
            try {

                int id = params[0];
                Uri.Builder builder = new Uri.Builder();
                builder.scheme("http");
                builder.encodedAuthority(Constants.url);

                builder.appendPath("Shop")
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
                return getShopFromJsom(jsonWeatherResponse);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onPostExecute(Shop shop) {


            shopNameDetails.setText(shop.getName());
            final List<String> fruits_list = new ArrayList<String>();
            List<Address> addresses = shop.getAddresses();
            for (Address address:addresses) {
                String newAddress = address.getStreet()+ "\t" + address.getNumber() +"\t" + address.getCity().getName();
                fruits_list.add(newAddress);
            }

            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                    (mContext,android.R.layout.simple_list_item_1, fruits_list);

            listAddresses.setAdapter(arrayAdapter);



            final List<String> car_brands_list = new ArrayList<String>();
            for (CarBrand carBrand:shop.getCarBrands()) {
                car_brands_list.add(carBrand.getName());
            }
            final ArrayAdapter<String> carBrandsListAdapter = new ArrayAdapter<String>
                    (mContext,android.R.layout.simple_list_item_1, car_brands_list );

            carBrandsListView.setAdapter(carBrandsListAdapter);


        }
    }
 /*   private List<String> getAddressToShow(List<Address> addresses){
        final List<String> fruits_list = new ArrayList<String>();
        for (Address address:addresses) {
            String newAddress = address.getStreet()+ "\t" + address.getNumber();
            fruits_list.add(newAddress);
        }
    }*/
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

    private Shop getShopFromJsom(String json) {
        Gson gson = new Gson();
        Type listType = new TypeToken<Shop>() {}.getType();
        return gson.fromJson(json, listType);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }
}
