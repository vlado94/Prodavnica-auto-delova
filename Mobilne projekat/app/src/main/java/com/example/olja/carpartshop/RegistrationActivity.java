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
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.olja.carpartshop.country.CountriesActivity;
import com.example.olja.carpartshop.database.DataAccess;
import com.example.olja.carpartshop.user.User;

import org.json.JSONObject;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    Button submitBtn = null;
    private android.support.constraint.ConstraintLayout layout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_activity);
        getSupportActionBar().setTitle("Prodavnica auto delova");

        layout = (android.support.constraint.ConstraintLayout) findViewById(R.id.registration_layout);
        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        submitBtn = (Button)findViewById(R.id.reg_submit);
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
        String name = ((TextInputEditText)findViewById(R.id.reg_name_value)).getText().toString();
        String email = ((TextInputEditText)findViewById(R.id.reg_email_value)).getText().toString();
        String pass = ((TextInputEditText)findViewById(R.id.reg_password_value)).getText().toString();
        String pass2 = ((TextInputEditText)findViewById(R.id.reg_password2_value)).getText().toString();
        if(!pass.equals(pass2)){
            Toast.makeText(this, "Lozinke se ne poklapaju", Toast.LENGTH_SHORT).show();
            return;
        }
        User user = new User(email, pass, "");

        new RegistrationTask().execute(user);
    }


    public class RegistrationTask extends AsyncTask<User, Void, JSONObject> {

        @Override
        protected JSONObject doInBackground(User... user) {
            JSONObject json = DataAccess.sendPost("User", "Save", user[0]);
            return  json;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            if(jsonObject != null) {
                Toast.makeText(RegistrationActivity.this, "Uspesna registracija", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                startActivity(intent);
            }
            else {
                Toast.makeText(RegistrationActivity.this, "Neuspesno registracija, pokusajte ponovo.", Toast.LENGTH_SHORT).show();
            }
        }
    }


}
