package com.example.olja.carpartshop.carPart;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.olja.carpartshop.R;

/**
 * Created by Olja on 6/11/2018.
 */

public class CarPartInformationsActivity  extends AppCompatActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.car_part_details);

        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }




        Intent intentThatStartedThisActivity = getIntent();
        if (intentThatStartedThisActivity.hasExtra("carPartId")) {
            int id = intentThatStartedThisActivity.getIntExtra("carPartId", -1);
            Toast.makeText(this,
                    String.valueOf(id),
                    Toast.LENGTH_LONG).show();
            /*new ListCarPartsForShopActivity.GetCarPartsForShopTask().execute(id);*/
        }
    }
}
