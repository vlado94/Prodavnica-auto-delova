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
import android.widget.Toast;

import com.example.olja.carpartshop.country.CountriesActivity;
import com.example.olja.carpartshop.database.CarPartDatabase;
import com.example.olja.carpartshop.database.News;
import com.example.olja.carpartshop.news.NewsAdapter;
import com.example.olja.carpartshop.news.NewsDetailActivity;

import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NewsAdapter.NewsOnClickHandler {

    private RecyclerView mRecyclerView;
    private NewsAdapter newsAdapter;
    private CarPartDatabase database;
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
        newsAdapter = new NewsAdapter(this);
        mRecyclerView.setAdapter(newsAdapter);
        database = CarPartDatabase.getInstance(getApplicationContext());
        addNews();
        retrieveNews();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.countries){
            Context context = MainActivity.this;
            Class destinationActivity = CountriesActivity.class;
            Intent startChildActivityIntent = new Intent(context, destinationActivity);
            startActivity(startChildActivityIntent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(int newsId) {
        int textEntered = newsId;
        Context context = MainActivity.this;
        Class destinationActivity = NewsDetailActivity.class;
        Intent startChildActivityIntent = new Intent(context, destinationActivity);
        startChildActivityIntent.putExtra("newsId",textEntered);
        startActivity(startChildActivityIntent);
    }

    private void retrieveNews() {
        LiveData<List<News>> news = database.newsDao().loadAllNews();
        news.observe(this, new Observer<List<News>>() {
            @Override
            public void onChanged(@Nullable List<News> newsEntries) {
                newsAdapter.setNews(newsEntries);
            }
        });
    }


    private void addNews() {
        Date date = new Date();
        final News first = new News( "Vijest 1","dfssfsdfssdfsfsdfsfs",date);
        final News second = new News( "Vijest 2","dsdfsdfsfsfdfsasdaa",date);
        Executor.getInstance().diskIO().execute(new Runnable() {
           // @Override
            public void run() {
                database.newsDao().insertNews(first);
                database.newsDao().insertNews(second);
                //finish();
            }
        });
    }
}
