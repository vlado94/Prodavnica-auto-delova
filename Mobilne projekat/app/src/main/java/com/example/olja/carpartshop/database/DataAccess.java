package com.example.olja.carpartshop.database;

import android.content.Context;
import android.net.Uri;

import com.example.olja.carpartshop.Constants;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class DataAccess {

    public static JSONObject sendPost(final String controller, final String method, final Object obj) {
        JSONObject retVal = null;
        try {
            URL url = new URL("http://"+Constants.url+"/"+controller+"/" + method);
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
            retVal = new JSONObject(output);
            bufferedReader.close();
            os.flush();
            os.close();


            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retVal;
    }

    public static JSONObject sendGet(final String controller, final String method, HashMap<String, String> parameters){
        JSONObject retVal = null;
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
            HttpURLConnection urlConnection = (HttpURLConnection) query.openConnection();
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
                if (!response.equals("")) {
                    retVal = new JSONObject(response);
                }
            }
            catch(Exception ex){
                int a=1;
            }
            finally {
                urlConnection.disconnect();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retVal;
    }

    public static JSONArray sendGetResultList(final String controller, final String method, HashMap<String, String> parameters){
        JSONArray retVal = null;
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
            HttpURLConnection urlConnection = (HttpURLConnection) query.openConnection();
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
                if (!response.equals("[]")) {
                    retVal = new JSONArray(response);
                }
            }
            finally {
                urlConnection.disconnect();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retVal;
    }



}
