package com.example.olja.carpartshop;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.olja.carpartshop.carPart.CarPart;
import com.example.olja.carpartshop.database.DataAccess;
import com.example.olja.carpartshop.shop.ListCarPartsForShopActivity;
import com.example.olja.carpartshop.shop.ListShopFragment;
import com.example.olja.carpartshop.shop.Shop;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Olja on 6/12/2018.
 */

public class SearchShopNewFragment extends Fragment {

    List<Shop> result;
    private static FragmentManager fragmentManager;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.search_shops_new, container, false);
        getActivity().setTitle("Pretraga prodavnica");

        fragmentManager = getActivity().getSupportFragmentManager();
        Button button = (Button) view.findViewById(R.id.search_shops_Btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = ((TextInputEditText) view.findViewById(R.id.search_shop_nameValue)).getText().toString();
                String carBrand = ((Spinner) view.findViewById(R.id.search_shop_carBrands)).getSelectedItem().toString();
                String city = ((TextInputEditText) view.findViewById(R.id.search_shop_addressValue)).getText().toString();

                HashMap<String, String> parameters = new HashMap<String, String>();
                parameters.put("name", name);
                parameters.put("carBrand", carBrand);
                parameters.put("city", city);


                new SearchShopNewFragment.ShopSearchTask().execute(parameters);
                int a = 3;
            }
        });
        return view;
    }

    public class ShopSearchTask extends AsyncTask<HashMap, Void, JSONArray> {

        @Override
        protected JSONArray doInBackground(HashMap... params) {
            JSONArray json = DataAccess.sendGetResultList("Shop", "Search", params[0]);
            return  json;
        }

        @Override
        protected void onPostExecute(JSONArray jsonObject) {
            super.onPostExecute(jsonObject);

            Toast.makeText(SearchShopNewFragment.this.getActivity(), "Pretraga...", Toast.LENGTH_SHORT).show();
            if(jsonObject != null) {
                result = new Gson().fromJson(jsonObject.toString(), new TypeToken<ArrayList<Shop>>(){}.getType());
                Fragment argumentFragment = new ListShopFragment();
                Bundle data = new Bundle();
                data.putParcelableArrayList("data", (ArrayList<? extends Parcelable>) result);
                argumentFragment.setArguments(data);
                fragmentManager.beginTransaction().replace(R.id.fragmentContainer, argumentFragment).commit();//now replace the argument fragment
            }
            else {
                Toast.makeText(SearchShopNewFragment.this.getActivity(), "Nema rezultata sa tim parametrima", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
