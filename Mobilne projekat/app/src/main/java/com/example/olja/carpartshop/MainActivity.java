package com.example.olja.carpartshop;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.olja.carpartshop.country.CountriesActivity;
import com.example.olja.carpartshop.news.NewsAdapter;
import com.example.olja.carpartshop.news.NewsDetailActivity;
import com.example.olja.carpartshop.services.getFromLinkIntentService;
import com.example.olja.carpartshop.shop.ShopAdapter;

public class MainActivity extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener {


    private static final int DEFAULT_TASK_ID = -1;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;

    private int mTaskId = DEFAULT_TASK_ID;

    private SectionsStatePageAdapter  mSectionStateAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSectionStateAdapter = new SectionsStatePageAdapter(getSupportFragmentManager());
        mViewPager =  findViewById(R.id.container);
        setupViewPager(mViewPager);


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close );

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setNavigationViewListener();
        setViewPager(6);

        Intent intentToSyncImmediately = new Intent(this, getFromLinkIntentService.class);
       this.startService(intentToSyncImmediately);

    }


    private void setupViewPager(ViewPager viewPager){
        SectionsStatePageAdapter adapter = new SectionsStatePageAdapter(getSupportFragmentManager());
        adapter.addFragment(new HomeFragment(), "HomeFragment");
        adapter.addFragment(new SearchFragment(), "SearchFragment");
        adapter.addFragment(new DetailSearchFragment(), "DetailSearchFragment");
        adapter.addFragment(new ShopsSearchFragment(), "ShopsSearchFragment");
        adapter.addFragment(new MostRecentFragment(), "MostRecentFragment");
        adapter.addFragment(new MostPopularFragment(), "MostPopularFragment");
        adapter.addFragment(new NewsFragment(), "NewsFragment");
        adapter.addFragment(new NotificationsFragment(), "NotificationsFragment");
        adapter.addFragment(new TermsOfUseFragment(), "TermsOfUseFragment");

        viewPager.setAdapter(adapter);
    }

    public void setViewPager(int fragmentNumber){
        mViewPager.setCurrentItem(fragmentNumber);
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

        if(id == R.id.countries){
            Context context = MainActivity.this;
            Class destinationActivity = CountriesActivity.class;
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
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        switch (item.getItemId()) {

            case R.id.home: {
                setViewPager(0);
                break;
            }
            case R.id.search: {
                setViewPager(1);
                break;
            }
            case R.id.detailSearch: {
                setViewPager(2);
                break;
            }
            case R.id.shopsSearch: {
                setViewPager(3);
                break;
            }
            case R.id.mostRecent: {
                setViewPager(4);
                break;
            }
            case R.id.mostPopular: {
                setViewPager(5);
                break;
            }
            case R.id.news: {
                setViewPager(6);
                break;
            }
            case R.id.notifications: {
                setViewPager(7);
                break;
            }
            case R.id.termsOfUse: {
                setViewPager(8);
                break;
            }
        }
        //close navigation drawer
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
