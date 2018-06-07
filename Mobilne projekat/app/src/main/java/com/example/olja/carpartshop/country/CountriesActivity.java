package com.example.olja.carpartshop.country;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.olja.carpartshop.Executor;
import com.example.olja.carpartshop.R;
import com.example.olja.carpartshop.city.CitiesActivity;
import com.example.olja.carpartshop.database.CarPartDatabase;
import com.example.olja.carpartshop.database.Country;

import java.util.List;

public class CountriesActivity extends AppCompatActivity implements CountriesAdapter.CountryOnClickHandler  {

    private RecyclerView mRecyclerView;
    private CountriesAdapter countriesAdapter;
    private CarPartDatabase database;
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
        countriesAdapter = new CountriesAdapter(this);
        mRecyclerView.setAdapter(countriesAdapter);
        database = CarPartDatabase.getInstance(getApplicationContext());
        addCountries();
        retrieveCountries();

    }

    @Override
    public void onClick(int countryId) {
        Context context = CountriesActivity.this;
        Class destinationActivity = CitiesActivity.class;
        Intent startChildActivityIntent = new Intent(context, destinationActivity);
        startChildActivityIntent.putExtra("countryId",countryId);
        startActivity(startChildActivityIntent);
    }

    private void retrieveCountries() {

        LiveData<List<Country>> news = database.countryDao().loadAllCountries();
        news.observe(this, new Observer<List<Country>>() {
            @Override
            public void onChanged(@Nullable List<Country> newsEntries) {
                countriesAdapter.setNews(newsEntries);
            }
        });
    }


    private void addCountries() {
        final Country first = new Country("Drzavva 1", false);
        final Country second = new Country("Drzava 2",false);
        Executor.getInstance().diskIO().execute(new Runnable() {
            // @Override
            public void run() {

                database.countryDao().insertCountry(first);
                database.countryDao().insertCountry(second);
                //finish();
            }
        });
    }
}
