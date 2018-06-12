package com.example.olja.carpartshop;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.olja.carpartshop.carPart.CarPartAdapter;
import com.example.olja.carpartshop.carPart.MyShopCarPartAdapter;
import com.example.olja.carpartshop.database.DataAccess;
import com.example.olja.carpartshop.shop.ListCarPartsForShopActivity;
import com.example.olja.carpartshop.shop.Shop;
import com.example.olja.carpartshop.user.User;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Nebojsa on 6/11/2018.
 */

public class MyShopFragment extends android.support.v4.app.Fragment  implements MyShopCarPartAdapter.CarPartOnClickHandler {
    private static final String TAG = "TermsOfUseFragment";

    private Button addProductBtn;
    private TextView shopName;
    private RecyclerView carPartRecyclerView;
    private MyShopCarPartAdapter cartPartAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_shop, container, false);

        addProductBtn = view.findViewById(R.id.add_product);
        shopName = view.findViewById(R.id.shop_name);

        addProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Constants.currentCarPart = 0;
                Intent intent = new Intent(getActivity(), AddProductActivity.class);
                startActivity(intent);
            }
        });

        HashMap<String, String> parameters = new HashMap<String, String>();
        parameters.put("id", Integer.toString(Constants.getLoggedUser().getID()));





        carPartRecyclerView =  view.findViewById(R.id.my_shop_parts);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this.getActivity(), LinearLayoutManager.VERTICAL, false);
        carPartRecyclerView.setLayoutManager(layoutManager);
        carPartRecyclerView.setHasFixedSize(true);
        cartPartAdapter = new MyShopCarPartAdapter(this, getContext());
        carPartRecyclerView.setAdapter(cartPartAdapter);


        new LoadShopTask().execute(parameters);
        return  view;
    }

    @Override
    public void onClick(int newsId) {

    }


    public class LoadShopTask extends AsyncTask<HashMap, Void, JSONObject> {

        @Override
        protected JSONObject doInBackground(HashMap... user) {
            JSONObject json = DataAccess.sendGet("Shop", "GetByUserID", user[0]);

            return  json;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            if(jsonObject != null) {
                Shop shop = new Gson().fromJson(jsonObject.toString(), Shop.class);
                shopName.setText(shop.getName());
                cartPartAdapter.setCarParts(shop.getCarParts());
            }
            else {
                Toast.makeText(MyShopFragment.this.getActivity(), "Greska", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
