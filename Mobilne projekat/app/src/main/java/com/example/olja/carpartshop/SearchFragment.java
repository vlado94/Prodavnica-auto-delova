package com.example.olja.carpartshop;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.olja.carpartshop.carPart.CarPart;
import com.example.olja.carpartshop.database.DataAccess;
import com.example.olja.carpartshop.shop.ListCarPartsForShopActivity;
import com.example.olja.carpartshop.shop.ShopDetailsActivity;
import com.example.olja.carpartshop.user.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Nebojsa on 6/1/2018.
 */

public class SearchFragment extends Fragment {
    private static final String TAG = "SearchFragment";

    List<CarPart> result;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.search_fragment, container, false);
        getActivity().setTitle("Pretraga proizvoda");

        Button button = (Button) view.findViewById(R.id.searchBtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String carPart = ((TextInputEditText) view.findViewById(R.id.carPartValue)).getText().toString();
                String carBrand = ((Spinner) view.findViewById(R.id.carBrand)).getSelectedItem().toString();
                String maxPrice = ((TextInputEditText) view.findViewById(R.id.maxPriceValue)).getText().toString();
                String minPrice = ((TextInputEditText) view.findViewById(R.id.minPriceValue)).getText().toString();

                HashMap<String, String> parameters = new HashMap<String, String>();
                parameters.put("carPart", carPart);
                parameters.put("carBrand", carBrand);
                parameters.put("maxPrice", maxPrice);
                parameters.put("minPrice", minPrice);

                new CarPartSearchTask().execute(parameters);
                int a = 3;
            }
        });
        return view;
    }



    public class CarPartSearchTask extends AsyncTask<HashMap, Void, JSONArray> {

        @Override
        protected JSONArray doInBackground(HashMap... params) {
            JSONArray json = DataAccess.sendGetResultList("CarPart", "Search", params[0]);
            return  json;
        }

        @Override
        protected void onPostExecute(JSONArray jsonObject) {
            super.onPostExecute(jsonObject);

            Toast.makeText(SearchFragment.this.getActivity(), "Pretraga...", Toast.LENGTH_SHORT).show();
            if(jsonObject != null) {
                result = new Gson().fromJson(jsonObject.toString(), new TypeToken<ArrayList<CarPart>>(){}.getType());
                //TODO Olja, ovaj rezultat prikazi u fragmentu za prikaz proizvoda :*
                Context context = getActivity();
                Class destinationActivity = ListCarPartsForShopActivity.class;
                Intent startChildActivityIntent = new Intent(context, destinationActivity);
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("data", (ArrayList<? extends Parcelable>) result);
                startChildActivityIntent.putExtras(bundle);
                startActivity(startChildActivityIntent);
            }
            else {
                Toast.makeText(SearchFragment.this.getActivity(), "Nema rezultata sa tim parametrima", Toast.LENGTH_SHORT).show();
            }
        }
    }



}
