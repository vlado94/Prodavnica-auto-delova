package com.example.olja.carpartshop;

import android.net.Uri;
import android.os.AsyncTask;

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
import java.util.List;
import java.util.Scanner;

public class Constants {

    public static String url = "192.168.0.11:52387";



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



    public static void sendPost(final String controller, final String methond, final Object obj) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://192.168.0.11:52387/"+controller+"/" + methond);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                    conn.setRequestProperty("Accept","application/json");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);
                    DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                    //os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
                    Gson gson = new Gson();
                    os.writeBytes(gson.toJson(obj));
                    int responseCode = conn.getResponseCode();

                    // get response
                    BufferedReader bufferedReader = null;
                    if (responseCode > 199 && responseCode < 300) {
                        bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    } else {
                        bufferedReader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                    }

                    // To receive the response
                    StringBuilder content = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        content.append(line).append("\n");
                    }
                    String output = content.toString();
                    bufferedReader.close();
                    os.flush();
                    os.close();


                    conn.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }

}
