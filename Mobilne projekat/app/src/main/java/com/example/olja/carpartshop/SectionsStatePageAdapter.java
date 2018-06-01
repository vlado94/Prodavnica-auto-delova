package com.example.olja.carpartshop;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;
import java.util.ArrayList;

/**
 * Created by Nebojsa on 6/1/2018.
 */

public class SectionsStatePageAdapter extends FragmentStatePagerAdapter {

    private final  List<Fragment> mFragmentList = new ArrayList<>();
    private final  List<String> mFragmentTitleList = new ArrayList<>();

    public SectionsStatePageAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addFragment(Fragment fragment, String title){
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }
}
