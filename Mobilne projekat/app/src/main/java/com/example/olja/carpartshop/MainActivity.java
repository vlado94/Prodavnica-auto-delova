package com.example.olja.carpartshop;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements CarPartAdapter.CarPartOnClickHandler {
    private RecyclerView mRecyclerView;
    private CarPartAdapter mForecastAdapter;

    private TextView mErrorMessageDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_forecast);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mForecastAdapter = new CarPartAdapter(this);
        mRecyclerView.setAdapter(mForecastAdapter);
        String[] weatherData ={"priv", "ddrugi","treci"};
        mForecastAdapter.setListData(weatherData);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       // return super.onCreateOptionsMenu(menu);

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.ikonica) {

            CharSequence text = "Klik na ikonicu!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(this, text, duration);
            toast.show();
        }
        return super.onOptionsItemSelected(item);
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

        String textEntered = weatherForDay;
        Context context = MainActivity.this;
        Class destinationActivity = CarPartDetailActivity.class;
        Intent startChildActivityIntent = new Intent(context, destinationActivity);
        startChildActivityIntent.putExtra(Intent.EXTRA_TEXT, textEntered);
        startActivity(startChildActivityIntent);
    }
}
