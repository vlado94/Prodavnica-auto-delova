package com.example.olja.carpartshop.database;

import android.content.Context;

import com.example.olja.carpartshop.Constants;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DataAccess {

    public static JSONObject sendPost(final String controller, final String methond, final Object obj) {
        JSONObject retVal = null;
        try {
            URL url = new URL("http://"+Constants.url+"/"+controller+"/" + methond);
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
}
