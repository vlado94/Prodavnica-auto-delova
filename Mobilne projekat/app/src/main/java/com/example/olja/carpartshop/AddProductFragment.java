package com.example.olja.carpartshop;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.olja.carpartshop.carBrand.CarBrand;
import com.example.olja.carpartshop.carPart.CarPart;
import com.example.olja.carpartshop.database.DataAccess;
import com.example.olja.carpartshop.shop.Shop;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nebojsa on 6/11/2018.
 */

public class AddProductFragment extends Fragment implements View.OnClickListener{
    private static final String TAG = "AddProductFragment";

    private EditText partName = null;
    private Spinner partBrand = null;
    private EditText partPrice = null;
    private EditText partQuantity = null;
    private EditText partShortDesc = null;
    private EditText partLongDesc = null;
    private Button partSubmit = null;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_product, container, false);

        partName = view.findViewById(R.id.cp_name);
        partBrand = view.findViewById(R.id.cp_brand);
        partPrice = view.findViewById(R.id.cp_price);
        partQuantity = view.findViewById(R.id.cp_quantity);
        partShortDesc = view.findViewById(R.id.cp_short_desc);
        partLongDesc = view.findViewById(R.id.cp_long_desc);
        partSubmit = view.findViewById(R.id.cp_submit);

        partSubmit.setOnClickListener(this);

        return  view;
    }

    @Override
    public void onClick(View view) {
        if(Constants.getLoggedUser() != null) {
            CarPart carPart = new CarPart();
            carPart.setName(partName.getText().toString());
            CarBrand cb = new CarBrand();
            cb.setName(partBrand.getSelectedItem().toString());
            carPart.setCarBrand(cb);
            carPart.setPrice(Integer.parseInt(partPrice.getText().toString()));
            carPart.setQuantity(Integer.parseInt(partQuantity.getText().toString()));
            carPart.setShortDescription(partShortDesc.getText().toString());
            carPart.setLongDescription(partLongDesc.getText().toString());
            carPart.setUserID(Constants.getLoggedUser().getID());

            new AddProductFragment.PostCarPartTask().execute(carPart);
        }
        else
            Toast.makeText(AddProductFragment.this.getActivity(), "Greska.", Toast.LENGTH_SHORT).show();
    }



    public class PostCarPartTask extends AsyncTask<CarPart, Void, JSONObject> {

        @Override
        protected JSONObject doInBackground(CarPart... part) {
            JSONObject json = DataAccess.sendPost("CarPart", "Save", part[0]);
            return  json;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            if(jsonObject != null) {
                Constants.hasShop = true;
                Toast.makeText(AddProductFragment.this.getActivity(), "Uspesno dodata prodavnica", Toast.LENGTH_SHORT).show();
                getActivity().recreate();
            }
            else {
                Toast.makeText(AddProductFragment.this.getActivity(), "Greska.", Toast.LENGTH_SHORT).show();
            }
        }
    }



}
