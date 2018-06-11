package com.example.olja.carpartshop;

import android.*;
import android.Manifest;
import android.Manifest.permission;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.olja.carpartshop.carBrand.CarBrand;
import com.example.olja.carpartshop.carPart.CarPart;
import com.example.olja.carpartshop.database.DataAccess;
import com.example.olja.carpartshop.shop.Shop;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.app.Activity.RESULT_OK;

/**
 * Created by Nebojsa on 6/11/2018.
 */

public class AddProductFragment extends Fragment implements View.OnClickListener{
    private static final String TAG = "AddProductFragment";
    private static final int STORAGE_PERMISION_CODE = 2342;
    private static final int PICK_IMAGE_REQUEST = 22;


    private EditText partName = null;
    private Spinner partBrand = null;
    private EditText partPrice = null;
    private EditText partQuantity = null;
    private EditText partShortDesc = null;
    private EditText partLongDesc = null;
    private Button partSubmit = null;
    private Button chooseImgBtn = null;
    private ImageView imageToUpload = null;

    private Uri filePath;
    private Bitmap bitmap;

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
        imageToUpload = view.findViewById(R.id.image_to_upload);

        chooseImgBtn = view.findViewById(R.id.cp_choose_img);
        partSubmit.setOnClickListener(this);
        chooseImgBtn.setOnClickListener(this);
        requestStoragePermission();

        return  view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(Constants.currentCarPart != 0)
            Toast.makeText(this.getActivity(), "IZmena", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this.getActivity(), "Dodavanje", Toast.LENGTH_LONG).show();
    }

    private void requestStoragePermission(){
        if(this.getActivity().checkSelfPermission(READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;
        getActivity().requestPermissions(new String[]{READ_EXTERNAL_STORAGE}, STORAGE_PERMISION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == STORAGE_PERMISION_CODE){
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this.getActivity(), "Permision granted", Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(this.getActivity(), "Permision not granted", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void showFileChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent.createChooser(intent, "Odaberi sliku"), PICK_IMAGE_REQUEST);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
            filePath = data.getData();
            try{
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
                bitmap = Bitmap.createScaledBitmap(bitmap, 600, 540, false);

                imageToUpload.setImageBitmap(bitmap);
            }
            catch (IOException io){

            }
        }
    }

    @Override
    public void onClick(View view) {
        if(view == partSubmit) {
            if (Constants.getLoggedUser() != null) {
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
                if(bitmap != null){
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte[] byteArray = stream.toByteArray();

                    if (bitmap != null && !bitmap.isRecycled()) {
                        bitmap.recycle();
                        bitmap = null;
                    }
                    String img64 = Base64.encodeToString(byteArray, Base64.DEFAULT);
                    carPart.setImage64(img64);
                }

                new AddProductFragment.PostCarPartTask().execute(carPart);
            } else
                Toast.makeText(AddProductFragment.this.getActivity(), "Greska.", Toast.LENGTH_SHORT).show();
        }

        if(view == chooseImgBtn)
        {
            showFileChooser();

        }



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
                Toast.makeText(AddProductFragment.this.getActivity(), "Uspesno dodat deo", Toast.LENGTH_SHORT).show();
                getActivity().recreate();
            }
            else {
                Toast.makeText(AddProductFragment.this.getActivity(), "Greska.", Toast.LENGTH_SHORT).show();
            }
        }
    }



}
