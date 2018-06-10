package com.example.olja.carpartshop;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.olja.carpartshop.database.DataAccess;
import com.example.olja.carpartshop.shop.Shop;
import com.example.olja.carpartshop.user.User;

import org.json.JSONObject;

/**
 * Created by Nebojsa on 6/10/2018.
 */

public class CreateShopFragment extends Fragment implements View.OnClickListener  {
    private static final String TAG = "CreateShopFragment";

    private EditText shopName;
    private EditText shopPhone;
    private EditText shopAddress;
    private Button shopSubmit;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.create_shop_fragment, container, false);

        shopName = view.findViewById(R.id.shop_name);
        shopPhone = view.findViewById(R.id.shop_phone);
        shopAddress = view.findViewById(R.id.shop_address);
        shopSubmit = view.findViewById(R.id.shop_submit);
        shopSubmit.setOnClickListener(this);

        return  view;
    }

    @Override
    public void onClick(View view) {
        if(Constants.getLoggedUser() != null) {
            Shop shop = new Shop();
            shop.setName(shopName.getText().toString());
            shop.setPhone(shopPhone.getText().toString());
            shop.setAddress(shopAddress.getText().toString());
            shop.setUserId(Constants.getLoggedUser().getID());

            new PostShopTask().execute(shop);
        }
        else
            Toast.makeText(CreateShopFragment.this.getActivity(), "Greska.", Toast.LENGTH_SHORT).show();
    }

    public class PostShopTask extends AsyncTask<Shop, Void, JSONObject> {

        @Override
        protected JSONObject doInBackground(Shop... shop) {
            JSONObject json = DataAccess.sendPost("Shop", "Save", shop[0]);
            return  json;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            if(jsonObject != null) {
                Toast.makeText(CreateShopFragment.this.getActivity(), "Uspesno dodata prodavnica", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CreateShopFragment.this.getActivity(), MainActivity.class);
                startActivity(intent);
            }
            else {
                Toast.makeText(CreateShopFragment.this.getActivity(), "Greska.", Toast.LENGTH_SHORT).show();
            }
        }
    }


}
