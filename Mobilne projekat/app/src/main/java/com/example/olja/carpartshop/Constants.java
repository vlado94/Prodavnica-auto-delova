package com.example.olja.carpartshop;

import android.net.Uri;
import android.os.AsyncTask;

import com.example.olja.carpartshop.user.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Constants {

    public static String url = "192.168.2.239:52387";
    public static User loggedUser = null;
    public static boolean hasShop = false;

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

    public static <T>List<T> getItemsFromServerWithSearch(String controller, String method, HashMap<String, String> parameters) {
        try {
            Uri.Builder builder = new Uri.Builder();
            builder.scheme("http");
            builder.encodedAuthority(Constants.url);

            builder.appendPath(controller)
                    .appendPath(method);


            Set set = parameters.entrySet();
            Iterator iterator = set.iterator();
            while (iterator.hasNext()) {
                Map.Entry mentry = (Map.Entry) iterator.next();
                builder.appendQueryParameter(mentry.getKey().toString(), mentry.getValue().toString());
            }
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

    public static User getLoggedUser() {
        return loggedUser;
    }

    public static void setLoggedUser(User loggedUser) {
        Constants.loggedUser = loggedUser;
    }

}
