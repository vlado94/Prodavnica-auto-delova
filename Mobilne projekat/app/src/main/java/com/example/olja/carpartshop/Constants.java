package com.example.olja.carpartshop;

import android.net.Uri;

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

public class Constants {

<<<<<<< HEAD
    public static String url = "192.168.137.43:52387";
=======
    public static String url = "192.168.137.118:52387";
>>>>>>> ecaccce1ba5a9110f183a3627c04600120504baa

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

    public static <E> List<E> getItemsFromJson(String json){
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<E>>(){}.getType();
        List<E> items =  gson.fromJson(json,listType);
        return items;
    }

    public static <T>List<T> getItemsFromServer(String controller,String methond){
        try {
            Uri.Builder builder = new Uri.Builder();
            builder.scheme("http");
            builder.encodedAuthority(Constants.url);

            builder.appendPath(controller)
                    .appendPath(methond);
            String myUrl = builder.build().toString();
            URL query = null;

            try {
                query = new URL(myUrl);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            String jsonWeatherResponse = getResponseFromHttpUrl(query);
            return getItemsFromJson(jsonWeatherResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<T>();
    }

}
