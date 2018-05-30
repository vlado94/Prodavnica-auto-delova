package com.example.olja.carpartshop.city;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by Olja on 5/30/2018.
 */

public class CitiesActivity  extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intentThatStartedThisActivity = getIntent();
        if (intentThatStartedThisActivity.hasExtra("countryId")) {
            int id = intentThatStartedThisActivity.getIntExtra("countryId", -1);

        }
    }
}
