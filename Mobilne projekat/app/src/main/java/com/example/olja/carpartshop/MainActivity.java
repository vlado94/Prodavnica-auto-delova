package com.example.olja.carpartshop;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.olja.carpartshop.database.CarPartDatabase;
import com.example.olja.carpartshop.database.News;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements CarPartAdapter.CarPartOnClickHandler {
    private RecyclerView mRecyclerView;
    private CarPartAdapter carPartAdapter;

    //private TextView mErrorMessageDisplay;
    private CarPartDatabase mDb;
    private static final int DEFAULT_TASK_ID = -1;

    private int mTaskId = DEFAULT_TASK_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_forecast);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        carPartAdapter = new CarPartAdapter(this);
        mRecyclerView.setAdapter(carPartAdapter);
       // ArrayList<News> weatherData =new ArrayList<News>();
        //weatherData.add(first);
        //weatherData.add(second);
        //carPartAdapter.setTasks(weatherData);

        mDb = CarPartDatabase.getInstance(getApplicationContext());
//mDb.clearAllTables();
        addNews();
        retrieveNews();
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
        }else if(id == R.id.drzave){
            //String textEntered = weatherForDay;
            Context context = MainActivity.this;
            Class destinationActivity = CountriesActivity.class;
            Intent startChildActivityIntent = new Intent(context, destinationActivity);
            //startChildActivityIntent.putExtra(Intent.EXTRA_TEXT, textEntered);
            startActivity(startChildActivityIntent);
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

    private void retrieveNews() {

        LiveData<List<News>> news = mDb.newsDao().loadAllNews();
        news.observe(this, new Observer<List<News>>() {
            @Override
            public void onChanged(@Nullable List<News> newsEntries) {
               // Log.d(TAG, "Receiving database update from LiveData");
                carPartAdapter.setNews(newsEntries);
            }
        });
    }


    private void addNews() {
        Date date = new Date();
        final News first = new News( "Vijest 1","dfssfsdfssdfsfsdfsfs",date);
        final News second = new News( "Vijest 2","dsdfsdfsfsfdfsasdaa",date);
        CarPartExecutor.getInstance().diskIO().execute(new Runnable() {
           // @Override
            public void run() {

                mDb.newsDao().insertNews(first);
                mDb.newsDao().insertNews(second);
                //finish();
            }
        });
    }
}
