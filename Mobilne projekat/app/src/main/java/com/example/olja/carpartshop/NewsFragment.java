package com.example.olja.carpartshop;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.olja.carpartshop.database.CarPartDatabase;
import com.example.olja.carpartshop.database.Country;
import com.example.olja.carpartshop.database.News;
import com.example.olja.carpartshop.news.NewsAdapter;

import java.util.Date;
import java.util.List;

/**
 * Created by Nebojsa on 6/1/2018.
 */

public class NewsFragment extends Fragment {
    private static final String TAG = "NewsFragment";


    private RecyclerView mRecyclerView;
    private NewsAdapter newsAdapter;
    private CarPartDatabase database;
    private Button btnTest;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.news_fragment, container, false);
        /*btnTest = view.findViewById(R.id.btnTest);

        btnTest.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Toast.makeText(getActivity(), "going to same fragment", Toast.LENGTH_SHORT).show();
                ((MainActivity)getActivity()).setViewPager(0);
            }
        });
*/

        mRecyclerView =  view.findViewById(R.id.recyclerview_forecast);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager((getActivity()), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        newsAdapter = new NewsAdapter(((MainActivity)getActivity()));
        mRecyclerView.setAdapter(newsAdapter);
        database = CarPartDatabase.getInstance((getActivity()).getApplicationContext());
        addNews();
        retrieveNews();
        List<Country> counties = retrieveCountries();
        //Log.d("Broj drzava", String.valueOf(counties.size()));


        return view;
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

    private List<Country> retrieveCountries(){

        LiveData<List<Country>> news = database.countryDao().loadAllCountries();
        news.observe(this, new Observer<List<Country>>() {
            @Override
            public void onChanged(@Nullable List<Country> countries) {

            }


        });
        return news.getValue();

    }


    private void addNews() {
        Date date = new Date();
        final News first = new News( "Vijest 1","dfssfsdfssdfsfsdfsfs",date,"Naslov1");
        final News second = new News( "Vijest 2","dsdfsdfsfsfdfsasdaa",date,"Naslov1");
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
