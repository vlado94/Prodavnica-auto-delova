package com.example.olja.carpartshop;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.olja.carpartshop.country.CountriesActivity;
import com.example.olja.carpartshop.services.getFromLinkIntentService;
import com.example.olja.carpartshop.user.User;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener {


    private static final int DEFAULT_TASK_ID = -1;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;

    private int mTaskId = DEFAULT_TASK_ID;

    private SectionsStatePageAdapter  mSectionStateAdapter;
    //private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSectionStateAdapter = new SectionsStatePageAdapter(getSupportFragmentManager());
        //mViewPager =  findViewById(R.id.container);
        //setupViewPager(mViewPager);


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close );

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Prodavnica auto delova");

        setNavigationViewListener();
        setFragment(new HomeFragment());
        ((NavigationView)findViewById(R.id.nav_view)).getMenu().getItem(0).setChecked(true);

        //Intent intentToSyncImmediately = new Intent(this, getFromLinkIntentService.class);
        //this.startService(intentToSyncImmediately);

        setLoggedUser();
        defaultSetup();
    }



    private void defaultSetup() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        String str = sp.getString("defaultbackground", "temp");
        if(str.equals("greenback")) {
            mDrawerLayout.setBackgroundResource(R.drawable.greenback);
        }
        else if(str.equals("whitepic")) {
            mDrawerLayout.setBackgroundResource(R.drawable.whitepic);
        } else {
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("defaultbackground", "whitepic");
            editor.apply();
            mDrawerLayout.setBackgroundResource(R.drawable.whitepic);
        }
    }

    public void setFragment(android.support.v4.app.Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.container2, fragment, "starterFragment");
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    protected void onResume() {
        defaultSetup();
        super.onResume();
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

        if(id == R.id.settingsfragment1){
            Context context = MainActivity.this;
            Class destinationActivity = SettingsActivity.class;
            Intent startChildActivityIntent = new Intent(context, destinationActivity);
            startActivity(startChildActivityIntent);
        }
        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*@Override
    public void onClick(int newsId) {
        Context context = MainActivity.this;
        Class destinationActivity = NewsDetailActivity.class;
        Intent startChildActivityIntent = new Intent(context, destinationActivity);
        startChildActivityIntent.putExtra("newsId",newsId);
        startActivity(startChildActivityIntent);
    }*/

    private void setNavigationViewListener() {
        NavigationView navigationView =  findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View r = navigationView.getHeaderView(0);
        ImageView loginBtn = (ImageView) r.findViewById(R.id.loginbtn);
        ImageView singUpBtn = (ImageView) r.findViewById(R.id.signupbtn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        singUpBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.

        android.support.v4.app.Fragment fragment = new HomeFragment();
        switch (item.getItemId()) {
            case R.id.home: {
                getSupportFragmentManager();
                fragment = new HomeFragment();
                break;
            }
            case R.id.search: {
                fragment = new SearchFragment();
                break;
            }
            case R.id.shopsSearch: {
                fragment = new ShopsSearchFragment();
                break;
            }
            case R.id.mostPopular: {
                fragment = new MostPopularFragment();
                break;
            }
            case R.id.news: {
                fragment = new NewsFragment();
                break;
            }
            case R.id.notifications: {
                fragment = new NotificationsFragment();
                break;
            }
            case R.id.termsOfUse: {
                fragment = new TermsOfUseFragment();
                break;
            }
            case R.id.search_shop: {
                fragment = new SearchShopNewFragment();
                break;
            }
            case R.id.addShop: {
                fragment = new CreateShopFragment();
                break;
            }
            case R.id.myShop: {
                fragment = new MyShopFragment();
                break;
            }
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.container2, fragment, "starterFragment");
        ft.addToBackStack(null);
        ft.commit();


        //close navigation drawer
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    private void setLoggedUser() {
        View hv = ((NavigationView)findViewById(R.id.nav_view)).getHeaderView(0);

        SharedPreferences prefs = getSharedPreferences("appData", MODE_PRIVATE);
        String loggedUserJsonStr = prefs.getString("loggedUser", null);
        if (loggedUserJsonStr != null) {
            User loggedUser = new Gson().fromJson(loggedUserJsonStr, User.class);
            if (loggedUser != null) {
                TextView tv = hv.findViewById(R.id.logged_user);
                tv.setText(loggedUser.getEmail());
                Constants.setLoggedUser(loggedUser);

                if(Constants.hasShop || loggedUser.getShopID() != 0){
                    ((NavigationView)findViewById(R.id.nav_view)).getMenu().findItem(R.id.addShop).setVisible(false);
                    ((NavigationView)findViewById(R.id.nav_view)).getMenu().findItem(R.id.myShop).setVisible(true);
                }
                else{
                    ((NavigationView)findViewById(R.id.nav_view)).getMenu().findItem(R.id.addShop).setVisible(true);
                    ((NavigationView)findViewById(R.id.nav_view)).getMenu().findItem(R.id.myShop).setVisible(false);
                }
            }
        }
        else{
            ((NavigationView)findViewById(R.id.nav_view)).getMenu().findItem(R.id.addShop).setVisible(false);
        }


    }

}
