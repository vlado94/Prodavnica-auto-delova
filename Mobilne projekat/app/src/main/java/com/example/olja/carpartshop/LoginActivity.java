package com.example.olja.carpartshop;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.olja.carpartshop.database.DataAccess;
import com.example.olja.carpartshop.news.News;
import com.example.olja.carpartshop.user.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    Button submitBtn = null;

    private android.support.constraint.ConstraintLayout layout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        layout = (android.support.constraint.ConstraintLayout) findViewById(R.id.login_layout);

        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        submitBtn = (Button)findViewById(R.id.log_submit);
        submitBtn.setOnClickListener(this);

        defaultSetup();
    }


    @Override
    protected void onResume() {
        defaultSetup();
        super.onResume();

    }

    private void defaultSetup() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        String str = sp.getString("defaultbackground", "temp");
        if(str.equals("greenback")) {
            layout.setBackgroundResource(R.drawable.greenback);
        }
        else if(str.equals("whitepic")) {
            layout.setBackgroundResource(R.drawable.whitepic);
        } else {
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("defaultbackground", "whitepic");
            editor.apply();
            layout.setBackgroundResource(R.drawable.whitepic);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return false;
    }

    @Override
    public void onClick(View view) {
        String email = ((TextInputEditText) findViewById(R.id.log_email_value)).getText().toString();
        String pass = ((TextInputEditText) findViewById(R.id.log_pass_value)).getText().toString();
        String token = "";
        SharedPreferences prefs = getSharedPreferences("appData", MODE_PRIVATE);
        token = prefs.getString("firebaseToken", "");
        new LoginTask().execute(new User(email,pass,token));
    }


    public class LoginTask extends AsyncTask<User, Void, JSONObject> {

        @Override
        protected JSONObject doInBackground(User... user) {
            JSONObject json = DataAccess.sendPost("User", "Login", user[0]);

            return  json;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            if(jsonObject != null) {
                Toast.makeText(LoginActivity.this, "Uspesno logovanje", Toast.LENGTH_SHORT).show();
                SharedPreferences.Editor editor = getSharedPreferences("appData", MODE_PRIVATE).edit();
                editor.putString("loggedUser", jsonObject.toString());
                editor.apply();
                Constants.hasShop = false;
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
            else {
                Toast.makeText(LoginActivity.this, "Neuspesno logovanje, pokusajte ponovo", Toast.LENGTH_SHORT).show();
                ((TextInputEditText) findViewById(R.id.log_email_value)).setText("");
                ((TextInputEditText) findViewById(R.id.log_pass_value)).setText("");
            }
        }
    }
}
