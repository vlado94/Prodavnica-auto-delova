package com.example.olja.carpartshop;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.olja.carpartshop.country.CountryAdapter;
import com.example.olja.carpartshop.database.CarPartDatabase;
import com.example.olja.carpartshop.database.Country;
import com.example.olja.carpartshop.database.News;

import java.util.Date;
import java.util.List;

public class CountriesActivity extends AppCompatActivity implements CountryAdapter.CountryOnClickHandler  {

    private RecyclerView mRecyclerView;
    private CountryAdapter countryAdapter;

    //private TextView mErrorMessageDisplay;
    private CarPartDatabase mDb;
    private static final int DEFAULT_TASK_ID = -1;

    private int mTaskId = DEFAULT_TASK_ID;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.countries_list);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_countries);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        countryAdapter = new CountryAdapter(this);
        mRecyclerView.setAdapter(countryAdapter);
        mDb = CarPartDatabase.getInstance(getApplicationContext());
        addCountry();
        retrieveCountries();


    }

    @Override
    public void onClick(String weatherForDay) {

        //int duration = Toast.LENGTH_SHORT;
        // Toast toast = Toast.makeText(this, weatherForDay, duration);
        //toast.show();
        // COMPLETED (3) Remove the Toast and launch the DetailActivity using an explicit Intent
        //Class destinationClass = DetailActivity.class;
        //Intent intentToStartDetailActivity = new Intent(context, destinationClass);
        //startActivity(intentToStartDetailActivity);

       /* String textEntered = weatherForDay;
        Context context = MainActivity.this;
        Class destinationActivity = CarPartDetailActivity.class;
        Intent startChildActivityIntent = new Intent(context, destinationActivity);
        startChildActivityIntent.putExtra(Intent.EXTRA_TEXT, textEntered);
        startActivity(startChildActivityIntent);*/
    }

    private void retrieveCountries() {

        LiveData<List<Country>> news = mDb.countryDao().loadAllCountries();
        news.observe(this, new Observer<List<Country>>() {
            @Override
            public void onChanged(@Nullable List<Country> newsEntries) {
                // Log.d(TAG, "Receiving database update from LiveData");
                countryAdapter.setNews(newsEntries);
            }
        });

        Log.d("taff", "Zavrio dodavanmje");
    }


    private void addCountry() {
        //Date date = new Date();
        final Country first = new Country("Drzavva 1");
        final Country second = new Country("Drzava 2");
        CarPartExecutor.getInstance().diskIO().execute(new Runnable() {
            // @Override
            public void run() {

                mDb.countryDao().insertCountry(first);
                mDb.countryDao().insertCountry(second);
                //finish();
            }
        });

        Log.d("taff", "Zavrio dodavanmje");
    }
}
