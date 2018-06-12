package com.example.olja.carpartshop.shop;

import android.content.Context;
import android.content.Intent;
import android.location.Geocoder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.olja.carpartshop.Constants;
import com.example.olja.carpartshop.R;
import com.example.olja.carpartshop.address.Address;
import com.example.olja.carpartshop.carBrand.CarBrand;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
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
    private ListView carBrandsListView;

    private TextView shopNameDetails;
    private ListView addresesListView;
    private ImageView viewCarPartsIcon;

    private int shopId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_details);
        shopNameDetails = (TextView) findViewById(R.id.shopNameDetails);
        addresesListView = (ListView) findViewById(R.id.addresesListView);
        carBrandsListView = (ListView) findViewById(R.id.carBrandsListView);
        viewCarPartsIcon = (ImageView) findViewById(R.id.viewCarPartsIcon);



    /*    ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Detalji prodavnice");
        }
*/


        if(savedInstanceState != null){
            shopId = savedInstanceState.getInt("shopId");
        }else {
            Intent intentThatStartedThisActivity = getIntent();
            if (intentThatStartedThisActivity.hasExtra("shopId")) {
                int id = intentThatStartedThisActivity.getIntExtra("shopId", -1);
                shopId = id;



            }
        }

        new ShopDetailsActivity.GetShopByIdTask(this).execute(shopId);
        viewCarPartsIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Class destinationActivity = ListCarPartsForShopActivity.class;
                Intent startChildActivityIntent = new Intent(getApplicationContext(), destinationActivity);
                startChildActivityIntent.putExtra("shopId", shopId);
                startActivity(startChildActivityIntent);
            }
        });


    }


    /*SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.shop_map);
      mapFragment.getMapAsync(new OnMapReadyCallback() {
          @Override
          public void onMapReady(GoogleMap googleMap) {
              Geocoder coder= new Geocoder(getApplicationContext());
              LatLng latLng = new LatLng(1.289545, 103.849972);
              googleMap.addMarker(new MarkerOptions().position(latLng)
                      .title("Prodavnica"));
            //  googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
              googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12.0f));
          }


      });*/

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        shopId = savedInstanceState.getInt("shopId");

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        outState.putInt("shopId",shopId);
        super.onSaveInstanceState(outState);

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("fa", "fsfafas");
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    public class GetShopByIdTask extends AsyncTask<Integer, Void, Shop> {
        private Context mContext;
        private double latitude;
        private  double longitude;

        public GetShopByIdTask (Context context){
            mContext = context;
        }
        @Override
        protected Shop doInBackground(Integer... params) {
            try {

                int id = params[0];
                shopId = id;
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

            final List<String> car_brands_list = new ArrayList<String>();
            for (CarBrand carBrand:shop.getCarBrands()) {
                car_brands_list.add(carBrand.getName());
            }
            final ArrayAdapter<String> carBrandsListAdapter = new ArrayAdapter<String>
                    (mContext,android.R.layout.simple_list_item_1, car_brands_list );


            final List<String> address_list = new ArrayList<String>();
            address_list.add(shop.getAddress());

            final ArrayAdapter<String> addresListAdapter = new ArrayAdapter<String>
                    (mContext,android.R.layout.simple_list_item_1, address_list );
            carBrandsListView.setAdapter(carBrandsListAdapter);
            addresesListView.setAdapter(addresListAdapter);
            latitude = shop.getLatitude();
            longitude = shop.getLatitude();

            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.shop_map);
            mapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    Geocoder coder= new Geocoder(getApplicationContext());
                    LatLng latLng = new LatLng(latitude, longitude);
                    googleMap.addMarker(new MarkerOptions().position(latLng)
                            .title("Prodavnica"));
                    //  googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                    //googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12.0f));
                }
            });
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
