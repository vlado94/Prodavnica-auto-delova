package com.example.olja.carpartshop;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Nebojsa on 6/1/2018.
 */

public class MostPopularFragment extends Fragment implements CarPartAdapter.CarPartOnClickHandler{
    private static final String TAG = "MostPopularFragment";
    List<CarPart> result;
    private RecyclerView carPartRecyclerView;
    private CarPartAdapter cartPartAdapter;
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
        new MostPopularFragment.GetCarPartsByPopularity().execute("AsyncTaskGetShops");
        return  view;
    }

    public class GetCarPartsByPopularity extends AsyncTask<String, Void, JSONArray> {

        @Override
        protected JSONArray doInBackground(String... params) {
            JSONArray json = DataAccess.sendGetResultList("CarPart", "GetAllByPopularity", new HashMap<String, String>());
            return  json;
        }


        @Override
        protected void onPostExecute(JSONArray jsonObject) {
            super.onPostExecute(jsonObject);

            result = new Gson().fromJson(jsonObject.toString(), new TypeToken<ArrayList<CarPart>>(){}.getType());
            cartPartAdapter.setCarParts(result);


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
