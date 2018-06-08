package com.example.olja.carpartshop;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.olja.carpartshop.news.News;
import com.example.olja.carpartshop.news.NewsAdapter;
import com.example.olja.carpartshop.news.NewsDetailActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Nebojsa on 6/1/2018.
 */

public class NewsFragment extends Fragment implements NewsAdapter.NewsOnClickHandler{
    private static final String TAG = "NewsFragment";
    private String host = "192.168.0.18:52387";
    //private List<News> listNews;


    private RecyclerView mRecyclerView;
    private NewsAdapter newsAdapter;

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
        newsAdapter = new NewsAdapter(this);
        mRecyclerView.setAdapter(newsAdapter);


        new GetNewsTask().execute("Nebitno");
        //List<Country> counties = retrieveCountries();



        return view;
    }



    public class GetNewsTask extends AsyncTask<String, Void, List<News>> {

        @Override
        protected List<News> doInBackground(String... params) {
            try {

                Uri.Builder builder = new Uri.Builder();
                builder.scheme("http");
                builder.encodedAuthority(host);

                builder.appendPath("News")
                        .appendPath("GetAll");
                String myUrl = builder.build().toString();
                URL weatherQueryUrl = null;

                try {
                    weatherQueryUrl = new URL(myUrl);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                String jsonWeatherResponse = getResponseFromHttpUrl(weatherQueryUrl);
                return getNewsFromJsom(jsonWeatherResponse);


            } catch (Exception e) {
                e.printStackTrace();

            }

            return null;
        }


        @Override
        protected void onPostExecute(List<News> list) {
            newsAdapter.setNews(list);
        }
    }


    private List<News> getNewsFromJsom(String json){
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<News>>(){}.getType();
        return  gson.fromJson(json,listType);

    }

  /*  private List<Country> retrieveCountries(){

        LiveData<List<Country>> news = database.countryDao().loadAllCountries();
        news.observe(this, new Observer<List<Country>>() {
            @Override
            public void onChanged(@Nullable List<Country> countries) {

            }


        });
        return news.getValue();

    }*/


  public static String getResponseFromHttpUrl(URL url) throws IOException {
      HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
      String response = null;
      try {
          InputStream in = urlConnection.getInputStream();

          Scanner scanner = new Scanner(in);
          scanner.useDelimiter("\\A");

          boolean hasInput = scanner.hasNext();

          if (hasInput) {
              response = scanner.next();
          }
          scanner.close();
          return response;
      } finally {
          urlConnection.disconnect();
      }

  }



    @Override
    public void onClick(int newsId) {
        Context context = this.getActivity();
        Class destinationActivity = NewsDetailActivity.class;
        Intent startChildActivityIntent = new Intent(context, destinationActivity);
        startChildActivityIntent.putExtra("newsId",newsId);
        startActivity(startChildActivityIntent);
    }
}
