package com.example.olja.carpartshop;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.olja.carpartshop.news.News;
import com.example.olja.carpartshop.news.NewsAdapter;
import com.example.olja.carpartshop.news.NewsDTO;
import com.example.olja.carpartshop.news.NewsDetailActivity;
import com.example.olja.carpartshop.shop.Shop;
import com.example.olja.carpartshop.shop.ShopAdapter;
import com.example.olja.carpartshop.shop.ShopDetailsActivity;
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
 * Created by Nebojsa on 6/1/2018.
 */

public class ShopsSearchFragment extends Fragment implements ShopAdapter.ShopOnClickHandler {
    private static final String TAG = "ShopsSearchFragment";
    private String host = "192.168.137.38:52387";

    private RecyclerView shopsRecyclerView;
    private ShopAdapter shopAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.shops_search_fragment, container, false);



        shopsRecyclerView =  view.findViewById(R.id.recyclerview_shops);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager((getActivity()), LinearLayoutManager.VERTICAL, false);
        shopsRecyclerView.setLayoutManager(layoutManager);
        shopsRecyclerView.setHasFixedSize(true);
        shopAdapter = new ShopAdapter(this);
        shopsRecyclerView.setAdapter(shopAdapter);

        new ShopsSearchFragment.GetShopsTask().execute("AsyncTaskGetShops");
        return  view;
    }



    public class GetShopsTask extends AsyncTask<String, Void, List<Shop>> {

        @Override
        protected List<Shop> doInBackground(String... params) {
            try {

                Uri.Builder builder = new Uri.Builder();
                builder.scheme("http");
                builder.encodedAuthority(host);

                builder.appendPath("Shop")
                        .appendPath("GetAll");
                String myUrl = builder.build().toString();
                URL weatherQueryUrl = null;

                try {
                    weatherQueryUrl = new URL(myUrl);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                String jsonWeatherResponse = getResponseFromHttpUrl(weatherQueryUrl);
                return getShopsFromJsom(jsonWeatherResponse);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onPostExecute(List<Shop> list) {
            shopAdapter.setShops(list);
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

    private List<Shop> getShopsFromJsom(String json){
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<Shop>>(){}.getType();
        return gson.fromJson(json,listType);

    }

    @Override
    public void onClick(int shopId) {
        Context context = this.getActivity();
        Class destinationActivity = ShopDetailsActivity.class;
        Intent startChildActivityIntent = new Intent(context, destinationActivity);
        startChildActivityIntent.putExtra("shopId",shopId);
        startActivity(startChildActivityIntent);
    }
}
